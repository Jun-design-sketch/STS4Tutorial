package com.example.todolist.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import com.example.todolist.dao.TodoDaoImpl;
import com.example.todolist.entity.Todo;
import com.example.todolist.form.TodoData;
// TodoQuery import合ってんのかな？
import com.example.todolist.form.TodoQuery;
import com.example.todolist.repository.TodoRepository;
import com.example.todolist.service.TodoService;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TodoListController {
  private final TodoRepository todoRepository;
  private final TodoService todoService;
  private final HttpSession session;

  @PersistenceContext
  private EntityManager entityManager;
  TodoDaoImpl todoDaoImpl;
  
  @PostConstruct
  public void init() {
	  todoDaoImpl = new TodoDaoImpl(entityManager);
  }
  
  // ToDo一覧を表示する
  @GetMapping("/todo")
  public ModelAndView showTodoList(ModelAndView mv, 
		  @PageableDefault(page = 0, size = 5, sort = "id") Pageable pageable) {
	  mv.setViewName("todoList");

	  Page<Todo> todoPage = todoRepository.findAll(pageable);
	  mv.addObject("todoQuery", new TodoQuery());
	  
	  mv.addObject("todoPage", todoPage);
	  mv.addObject("todoList", todoPage.getContent());
	  session.setAttribute("todoQuery", new TodoQuery());

	  
	  return mv;
  }
  
  //フォームに入力された条件でTodoを検索  
  @PostMapping("/todo/query")
  public ModelAndView queryTodo(@ModelAttribute TodoQuery todoQuery, BindingResult result, ModelAndView mv) {
	  mv.setViewName("todoList");
	  
	  List<Todo> todoList = null;
	  if(todoService.isValid(todoQuery, result)) {
		  //エラーがなければ検索
		  // todoList = todoService.doQuery(todoQuery);
		  // JPQLによる検索
		  todoList = todoDaoImpl.findByJPQL(todoQuery);
	  }
	  // mv.add Object("todoQuery", todoQuery);
	  mv.addObject("todoList", todoList);
	  
	  return mv;
  }

  // ToDo入力フォーム表示
  @GetMapping("/todo/create")
  public ModelAndView createTodo(ModelAndView mv) {
    mv.setViewName("todoForm");
    mv.addObject("todoData", new TodoData());
    session.setAttribute("mode", "create");
    return mv;
  }
  
  // ToDo追加処理
  @PostMapping("/todo/create")
  public String createTodo(@ModelAttribute @Validated TodoData todoData,
                                 BindingResult result, ModelAndView mv) {
    // エラーチェック
    boolean isValid = todoService.isValid(todoData, result);
    if (!result.hasErrors() && isValid) {
      // エラーなし
      Todo todo = todoData.toEntity();
      todoRepository.saveAndFlush(todo);
      return "redirect:/todo";

    } else {
      // エラーあり
      mv.setViewName("todoForm");
      // mv.addObject("todoData", todoData);
      return "todoForm";
    }
  }
   
  // Todo更新・削除（Todo詳細画面）
  @GetMapping("/todo/{id}")
  public ModelAndView todoById(@PathVariable(name = "id") int id, ModelAndView mv) {
	  mv.setViewName("todoForm");
	  
	  // オブジェクト Todoのtodoは、todoRepoの検索結果であると
	  Todo todo = todoRepository.findById(id).get();
	  mv.addObject("todoData", todo);
	  
	  // 「更新」であることをセッションに記録
	  session.setAttribute("mode", "update");
	  return mv;
  }

  @PostMapping("/todo/update")
  public String updateTodo(@ModelAttribute @Validated TodoData todoData, BindingResult result, Model model) {
	  boolean isValid = todoService.isValid(todoData, result);
	  if(!result.hasErrors() && isValid) {
		  //エラーなし
		  Todo todo = todoData.toEntity();
		  todoRepository.saveAndFlush(todo);
		  return "redirect:/todo";
	  }else{
		  //エラーあり
		  // model.addAttribute("todoData",todoData);
		  return "todoForm";
	  }
  }
  
  @PostMapping("/todo/delete")
  public String deleteTodo(@ModelAttribute TodoData todoData) {
	  todoRepository.deleteById(todoData.getId());
	  return "redirect:/todo";
  }
  
  // ToDo一覧へ戻る
  @PostMapping("/todo/cancel")
  public String cancel() {
    return "redirect:/todo";
  }
}
