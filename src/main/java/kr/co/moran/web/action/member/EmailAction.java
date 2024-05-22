package kr.co.moran.web.action.member;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.moran.web.action.Action;

public class EmailAction implements Action {
	// 보내는 사람 이메일
	private static final String USERNAME = "rladkgus75@naver.com";
	// 보내는 사람 비번
	private static final String PASSWORD = "KFTCVE7DN267";
	private static final String HOST = "smtp.naver.com";
	private static final String PORT = "587";

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String receiver=req.getParameter("email");
		

		Properties p = new Properties();
		
		//네이버 설정
		p.put("mail.smtp.enable", "true");
		p.put("mail.smtp.host",HOST);
		p.put("mail.smtp.auth","true");
		p.put("mail.smtp.port", PORT);
		
		// 보낼 메세지
		StringBuffer sb = new StringBuffer();		
		sb.append("<h3> 안녕하세요 </h3>");
		sb.append("<h3> 가입 테스트 메일 입니다. </h3>");
		String code = ""+(int)(Math.random()*100000);
		
		sb.append("<h3> 승인 번호는 "+code+" 입니다 </h3>");
				
		
		Session sessions = Session.getInstance(p, new Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});
		
		System.out.println("sessions : "+sessions);
		
		String title = "test 인증 번호입니다";
		
		Message message = new MimeMessage(sessions);
		try {
			//보내는 사람
			message.setFrom(new InternetAddress(USERNAME, "test", "utf-8"));
			
			//받는사람
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
			
			//제목
			message.setSubject(title);
			
			//내용
			message.setContent(sb.toString(), "text/html;charset=utf-8");
			
			//발송
			Transport.send(message);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
