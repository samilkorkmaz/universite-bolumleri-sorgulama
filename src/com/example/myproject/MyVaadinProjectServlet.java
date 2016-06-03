package com.example.myproject;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@SuppressWarnings("serial")
public class MyVaadinProjectServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html;charset=UTF-8");

		String targetScoreType = req.getParameter("scoreType");

		double scoreUp = Integer.parseInt(req.getParameter("scoreUp"));
		double scoreDown = Integer.parseInt(req.getParameter("scoreDown"));

		List<String> closestPrograms = UniversitePuanHesaplama.getPrograms(UniversityScoreData2015.getRaw(),
				targetScoreType, scoreUp, scoreDown);
		String results = "<h3>" + String.format("2015 yýlý için %s puan türünün [%1.0f ; %1.0f] aralýðýndaki bölümler:",
				targetScoreType, scoreDown, scoreUp) + "</h3>";
		if (closestPrograms.isEmpty()) {
			results = results + "<b><font color=\"red\">Girilen deðerlere uygun bölüm bulunamadý.</font></b>";
		} else {
			for (String cp : closestPrograms) {
				results = results + getHTMLLine(cp);
			}
		}

		String html = Jsoup.connect("http://universite-sorgulama.appspot.com/index.html").get().html();
		Document doc = Jsoup.parse(html);
		// Modify existing HTML so that scoreType, scoreUp, scoreDown remain as the user has input:
		Elements sts = doc.getElementsByAttribute("name");

		// Set selected scoreType:
		Elements options = sts.get(0).getElementsByTag("option");
		for (Element option : options) {
			if (option.attr("value").equals(req.getParameter("scoreType"))) {
				option.attr("selected", "selected");
			} else {
				option.removeAttr("selected");
			}
		}

		sts.get(1).val(req.getParameter("scoreUp"));

		sts.get(2).val(req.getParameter("scoreDown"));

		// pre-pend modified HTML so that the input page is displayed above results:
		String modHTML = doc.toString();
		resp.getWriter().println(modHTML);

		resp.getWriter().write(results); //triggers JavaScript (JQuery) inside index.html

		resp.getWriter().println("<h3>Faydalý Linkler:</h3>");		
		resp.getWriter().println(
				"<a href=\"http://universitetercihleri.com/ygs-lys-tercih\">Üniversite Tercih Motoru</a></br>");
		resp.getWriter().println(
				"<a href=\"http://www.osym.gov.tr/belge/1-23595/2015-osys-yerlestirme-sonuclarina-iliskin-sayisal-bilgi-.html\">2015-ÖSYS Yerleþtirme Sonuçlarýna Ýliþkin Sayýsal Bilgiler</a></br>");
		resp.getWriter()
				.println("<a href=\"https://universitesecimim.withgoogle.com\">Google Üniversite Seçimim</a></br>");
		resp.getWriter().println("<a href=\"http://samilkorkmaz.blogspot.com\">Þamil's Corner</a></br>");
		resp.getWriter().println(
				"<p>Öneriler için email: <a href=\"mailto:samil.korkmaz@gmail.com?Subject=Üniversite%20Puan%20Hesaplama\" target=\"_top\">samil.korkmaz@gmail.com</a></p>");
	}

	public static String getHTMLLine(String str) {
		return str + "</br>";

	}

}
