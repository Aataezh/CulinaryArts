package ma.ac.esi.culinaryarts.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import ma.ac.esi.culinaryarts.service.UserService;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    
    private static final String RECAPTCHA_SECRET = "6LcQDNQsAAAAAGgOq66bJdeHbHdRLK5lod2LpDU5";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ✅ ÉTAPE 1 : Vérifier le CAPTCHA avant tout
        String captchaToken = request.getParameter("g-recaptcha-response");
        if (!verifyCaptcha(captchaToken)) {
            response.sendRedirect(request.getContextPath() + "/index.html?error=captcha");
            return; // On arrête ici, on ne va pas plus loin
        }

        // ✅ ÉTAPE 2 : CAPTCHA validé → vérifier les credentials
        String login = request.getParameter("email");
        String password = request.getParameter("password");
        UserService userService = new UserService();

        try {
            if (userService.finUserByCredentials(login, password)) {
                HttpSession session = request.getSession();
                session.setAttribute("user", login);
                response.sendRedirect(request.getContextPath() + "/recipes");
            } else {
                response.sendRedirect(request.getContextPath() + "/index.html?error=1");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Envoie le token à l'API Google et vérifie la réponse.
     * Utilise uniquement des classes Java standard — pas de dépendance externe.
     */
    private boolean verifyCaptcha(String token) {

        // Token absent ou vide → refus immédiat
        if (token == null || token.isEmpty()) return false;

        try {
            String postData = "secret=" + URLEncoder.encode(RECAPTCHA_SECRET, StandardCharsets.UTF_8)
                            + "&response=" + URLEncoder.encode(token, StandardCharsets.UTF_8);

            URL url = new URL("https://www.google.com/recaptcha/api/siteverify");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000); // 5 secondes max
            conn.setReadTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // Envoyer les données
            try (OutputStream os = conn.getOutputStream()) {
                os.write(postData.getBytes(StandardCharsets.UTF_8));
            }

            // Lire la réponse JSON de Google
            String jsonResponse = new String(
                conn.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
            );

            // Google renvoie {"success": true, ...} ou {"success": false, ...}
            return jsonResponse.contains("\"success\": true")
                || jsonResponse.contains("\"success\":true");

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Erreur réseau → on refuse par sécurité
        }
    }
}