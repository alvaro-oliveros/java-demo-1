package com.demo.productmanagement.controller;

import com.demo.productmanagement.model.Category;
import com.demo.productmanagement.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories/list";
    }

    @GetMapping("/new")
    public String showNewCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "categories/form";
    }

    @PostMapping
    public String saveCategory(@ModelAttribute Category category, RedirectAttributes redirectAttributes) {
        categoryService.saveCategory(category);
        redirectAttributes.addFlashAttribute("message", "Categoría guardada con éxito");
        return "redirect:/category";
    }

    @GetMapping("/edit/{id}")
    public String showEditCategoryForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Category> optionalCategory = categoryService.getCategoryById(id);

        if (optionalCategory.isPresent()) {
            model.addAttribute("category", optionalCategory.get());
            return "categories/form";
        } else {
            redirectAttributes.addFlashAttribute("error", "Categoría no encontrada");
            return "redirect:/category";
        }
    }

    @PostMapping("/update")
    public String updateCategory(@ModelAttribute Category category, RedirectAttributes redirectAttributes) {
        boolean updated = categoryService.updateCategory(category);

        if (updated) {
            redirectAttributes.addFlashAttribute("message", "Categoría actualizada con éxito");
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar la categoría");
        }

        return "redirect:/category";
    }

    @GetMapping("/view/{id}")
    public String viewCategory(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Category> optionalCategory = categoryService.getCategoryById(id);

        if (optionalCategory.isPresent()) {
            model.addAttribute("category", optionalCategory.get());
            return "categories/view";
        } else {
            redirectAttributes.addFlashAttribute("error", "Categoría no encontrada");
            return "redirect:/category";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean deleted = categoryService.deleteCategory(id);

        if (deleted) {
            redirectAttributes.addFlashAttribute("message", "Categoría eliminada con éxito");
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la categoría");
        }

        return "redirect:/category";
    }
}