package com.app.ProjectRESTApp;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {

	//@Autowired
	//@Required
	//private final JWTUtil jwtUtil;
	
	private static final String TOKEN_PREFIX = "Bearer";
	private static final String HEADER_STRING = "Authorization";

   /* @Autowired
    public Controller(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }*/

	
	@RequestMapping(path = "/post/{id}", method = RequestMethod.GET)
	public ResponseEntity getPost(@RequestHeader(value=HEADER_STRING) String header, @PathVariable(name="id") String postId) 
	//add exception handling for missing header
	{
		//check authentication
		String user = JWTUtil.authenticateJWT(header.replace(TOKEN_PREFIX, ""));
		if(user==null)
		{
			return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		else
		{
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set(HEADER_STRING, header);
			
			Post p = new Post();
			p.setTitle("This is post # "+postId);
			p.setContent("This is post content");
			p.setUser(user);
			
			return new ResponseEntity(p, responseHeaders, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public ResponseEntity<String> login(
			@RequestParam(value = "user", required = true) String user,
			@RequestParam(value = "pass", required = true) String pass) {
			//HttpServletResponse response) {
		
		//get pass from DB
		String tempPass = "123";
		
		if(pass.equals(tempPass))
		{
			String jwt = JWTUtil.generateToken(user);
			//response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + jwt);
			
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set(HEADER_STRING, TOKEN_PREFIX + " " + jwt);
			return new ResponseEntity<String>("Authenticated", responseHeaders, HttpStatus.OK);
			
	    
		}
		else
		{
			return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		

	}
	
	/*@ModelAttribute
	public void setVaryResponseHeader(HttpServletResponse response) {
	    response.setHeader("Vary", "Accept");
	}  */

}
