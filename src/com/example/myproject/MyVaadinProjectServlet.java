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
		String data = "<h3>" + String.format("2015 y�l� i�in %s puan t�r�n�n [%1.0f ; %1.0f] aral���ndaki b�l�mler:",
						targetScoreType, scoreDown, scoreUp) + "</h3>";
		if (closestPrograms.isEmpty()) {
			data = data + "<b><font color=\"red\">Girilen de�erlere uygun b�l�m bulunamad�.</font></b>";
		} else {
			for (String cp : closestPrograms) {
				data = data + getHTMLLine(cp);
			}
		}
		resp.getWriter().write(data); // triggers JavaScript (JQuery) inside index.html

		resp.getWriter().println("<h3>Faydal� Linkler:</h3>");
		resp.getWriter().println("<a href=\"http://www.osym.gov.tr/belge/1-23595/2015-osys-yerlestirme-sonuclarina-iliskin-sayisal-bilgi-.html\">2015-�SYS Yerle�tirme Sonu�lar�na �li�kin Say�sal Bilgiler</a></br>");
		resp.getWriter().println("<a href=\"https://universitesecimim.withgoogle.com\">Google �niversite Se�imim</a></br>");
		resp.getWriter().println("<a href=\"http://samilkorkmaz.blogspot.com\">�amil's Corner</a></br>");
		resp.getWriter().println("<p>�neriler i�in email: <a href=\"mailto:samil.korkmaz@gmail.com?Subject=�niversite%20Puan%20Hesaplama\" target=\"_top\">samil.korkmaz@gmail.com</a></p>");
		

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
