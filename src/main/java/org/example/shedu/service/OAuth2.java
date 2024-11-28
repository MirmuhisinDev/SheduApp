//package org.example.shedu.service;
//
//import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
//import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
//import com.google.api.client.auth.oauth2.TokenResponse;
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
//import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
//
//import java.io.IOException;
//import java.util.Arrays;
//
//public class GoogleOAuthExample {
//    private static final String CLIENT_ID = "259057700292";
//    private static final String CLIENT_SECRET = "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";
//    private static final String REDIRECT_URI = "http://localhost:8080";
//    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
//    private static final NetHttpTransport HTTP_TRANSPORT = new NetHttpTransport();
//
//    public static void main(String[] args) throws IOException {
//        // Authorization Flow-ni yaratish
//        AuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//                HTTP_TRANSPORT, JSON_FACTORY, CLIENT_ID, CLIENT_SECRET,
//                Arrays.asList("https://www.googleapis.com/auth/userinfo.profile",
//                        "https://www.googleapis.com/auth/userinfo.email"))
//                .build();
//
//        // Foydalanuvchini login sahifasiga yo'naltirish uchun URL
//        AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI);
//        System.out.println("Google bilan login qilish uchun URL: " + authorizationUrl);
//
//        // Bu yerda foydalanuvchi login qilgandan keyin qaytadigan kod bilan Access Token olish jarayonini qo'shing.
//    }
//}
