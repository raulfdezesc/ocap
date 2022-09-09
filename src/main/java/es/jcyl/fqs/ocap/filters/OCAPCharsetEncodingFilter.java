package es.jcyl.fqs.ocap.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OCAPCharsetEncodingFilter implements Filter {

	private String charEncoding = "utf-8";
	/**
	 * @see javax.servlet.Filter#destroy()
	 */
	public final void destroy() {
		;
	}

	/**
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public final void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		// TODO Auto-generated method stub
	    HttpServletRequest req = (HttpServletRequest) request;
	    HttpServletResponse res = (HttpServletResponse) response;
	    req.setCharacterEncoding(charEncoding);	 
	    filterChain.doFilter(req, res);
	}

	/**
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public final void init(FilterConfig filterConfig) throws ServletException {
		if(filterConfig.getInitParameter("charEncoding") != null){
			charEncoding = filterConfig.getInitParameter("charEncoding");
		}
		
	}

}