<#include "/java_copyright.include">
<#assign entityName = entity.name>
<#assign entityNameLower = entityName?uncap_first>
package net.${copyright}.controller.${namespace};

import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.${copyright}.controller.admin.BaseController;
import net.${copyright}.Message;
import net.${copyright}.Pageable;
import net.${copyright}.entity.BaseEntity;
import net.${copyright}.service.${namespace}.${entityName}Service;
import net.${copyright}.entity.${namespace}.${entityName};

<#include "/custom.include"/>

@Controller("${namespace}${entityName}Controller")
@RequestMapping("/${namespace}/${entityNameLower}")
public class ${entityName}Controller extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(${entityName}.class);

	@Inject
	private ${entityName}Service ${entityNameLower}Service;


	/**
	 * 添加
	 */
	@GetMapping("/add")
	public String add(ModelMap model) {
		return "${namespace}/${entityNameLower}/add";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public String save(${entityName} ${entityNameLower},RedirectAttributes redirectAttributes) {
		if (!isValid(${entityNameLower}, BaseEntity.Save.class)) {
			return ERROR_VIEW;
		}
		${entityNameLower}Service.save(${entityNameLower});
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list";
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(Long id, ModelMap model) {
		model.addAttribute("${entityNameLower}", ${entityNameLower}Service.find(id));
		return "${namespace}/${entityNameLower}/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public String update(${entityName} ${entityNameLower},RedirectAttributes redirectAttributes) {
		if (!isValid(${entityNameLower})) {
			return ERROR_VIEW;
		}
        ${entityNameLower}Service.update(${entityNameLower}, "id");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list";
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", ${entityNameLower}Service.findPage(pageable));
		return "${namespace}/${entityNameLower}/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public @ResponseBody Message delete(Long[] ids) {
		${entityNameLower}Service.delete(ids);
		return SUCCESS_MESSAGE;
	}

}