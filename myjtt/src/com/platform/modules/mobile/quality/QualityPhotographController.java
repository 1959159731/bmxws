package com.platform.modules.mobile.quality;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platform.common.utils.UploadUtils;
import com.platform.common.web.BaseController;

/**
 * 质量安全隐患（Mobile）Controller
 * @author Mr.Jia
 * @version 2018-03-25
 */
@Controller
@RequestMapping(value = "${adminPath}/mobile/quality")
public class QualityPhotographController extends BaseController {

	@RequestMapping(value = {"/list", ""})
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/quality/qualityPhotograph";
	}

	@RequestMapping(value = {"/AddImage", ""}) //, method = RequestMethod.POST)
	public String AddImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		new UploadUtils().uploadFile(request);
		return "modules/quality/qualityPhotograph";
	}
}