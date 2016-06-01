package com.example.myproject;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.*;

import com.example.myvaadinproject.MyvaadinprojectUI;

@SuppressWarnings("serial")
public class MyVaadinProjectServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html;charset=UTF-8");
		
		String targetScoreType = req.getParameter("scoreType");
		
		double scoreUp = Integer.parseInt(req.getParameter("scoreUp"));
		double scoreDown = Integer.parseInt(req.getParameter("scoreDown"));
		
		List<String> closestPrograms = UniversitePuanHesaplama.getPrograms(UniversityScoreData2015.getRaw(),
				targetScoreType, scoreUp, scoreDown);
		String data = "<h3>" + String.format("2015 yýlý için %s puan türünün [%1.0f ; %1.0f] aralýðýndaki bölümler:",
						targetScoreType, scoreDown, scoreUp) + "</h3>";
		if (closestPrograms.isEmpty()) {
			data = data + "<b><font color=\"red\">Girilen deðerlere uygun bölüm bulunamadý.</font></b>";
		} else {
			for (String cp : closestPrograms) {
				data = data + getHTMLLine(cp);
			}
		}
		resp.getWriter().write(data); // triggers JavaScript (JQuery) inside index.html

		resp.getWriter().println("<h3>Faydalý Linkler:</h3>");
		resp.getWriter().println("<a href=\"http://www.osym.gov.tr/belge/1-23595/2015-osys-yerlestirme-sonuclarina-iliskin-sayisal-bilgi-.html\">2015-ÖSYS Yerleþtirme Sonuçlarýna Ýliþkin Sayýsal Bilgiler</a></br>");
		resp.getWriter().println("<a href=\"https://universitesecimim.withgoogle.com\">Google Üniversite Seçimim</a></br>");
		resp.getWriter().println("<a href=\"http://samilkorkmaz.blogspot.com\">Þamil's Corner</a></br>");
		resp.getWriter().println("<p>Öneriler için email: <a href=\"mailto:samil.korkmaz@gmail.com?Subject=Üniversite%20Puan%20Hesaplama\" target=\"_top\">samil.korkmaz@gmail.com</a></p>");
		

		/*
		 * String scoreType = req.getParameter("scoreType"); int scoreDown =
		 * Integer.parseInt(req.getParameter("scoreDown")); int scoreUp =
		 * Integer.parseInt(req.getParameter("scoreUp")); int score =
		 * Integer.parseInt(req.getParameter("score"));
		 */

	}

	public static String getHTMLLine(String str) {
		return str + "</br>";

	}

}
