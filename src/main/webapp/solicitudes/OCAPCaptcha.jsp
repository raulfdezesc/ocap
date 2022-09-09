<%@page contentType="text/html" pageEncoding="UTF-8" autoFlush="true"%>
<%@ page import="java.io.*"%>
<%@ page import="java.awt.*"%>
<%@ page import="java.awt.image.*"%>
<%@ page import="java.awt.image.BufferedImage"%>
<%@ page import="javax.imageio.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.awt.geom.*"%>
<%

try{
   int width=100;
   int height=25;
   /*
   Random rdm=new Random();
   int rl=rdm.nextInt();
   String hash1 = Integer.toHexString(rl);
   String capstr=hash1.substring(0,5);
   HttpSession session_actual = request.getSession(true);
   String codigo_generado = (String) session_actual.getAttribute("key");
   //session_actual.setAttribute("key",capstr);
   if(codigo_generado == null){
      session_actual.setAttribute("key",capstr);
   }else{
      //session_actual.invalidate();
      //HttpSession nueva_session = request.getSession(true);
      //nueva_session.setAttribute("key",capstr);
      session_actual.removeAttribute("key");
      session_actual.setAttribute("key",capstr);
   }
   */
   /*
   HttpSession session_actual = request.getSession(true);
   String capstr = (String) session_actual.getAttribute("key");
   Color background = new Color(255,255,255);
   Color fbl = new Color(0,100,0);
   Font fnt=new Font("SansSerif",1,17);
   BufferedImage cpimg =new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
   Graphics g = cpimg.createGraphics();
   g.setColor(background);
   g.fillRect(0,0,width,height);
   g.setColor(fbl);
   g.setFont(fnt);
   g.drawString(capstr,15,20);
   g.setColor(background);
   g.drawLine(50,17,80,17);
   g.drawLine(50,22,80,22);
   response.setContentType("image/jpeg");
   ServletOutputStream strm = response.getOutputStream();
   ImageIO.write(cpimg,"jpeg",strm);
   cpimg.flush();
   strm.flush();
   strm.close();
}catch (Exception ex){
   out.println(ex.getMessage());
}
*/
   response.setContentType("image/jpg");

   Color backgroundColor = Color.orange;
   Color borderColor = Color.red;
   Color textColor = Color.white;
   Color circleColor = new Color(160,160,160);
   Font textFont = new Font("Arial", Font.PLAIN, 24);
   int charsToPrint = 5;
//int width = request.getParameter("width") != null ? Integer.parseInt(request.getParameter("width")) : 150;
//int height = request.getParameter("height") != null ? Integer.parseInt(request.getParameter("height")) : 40;
   int circlesToDraw = 4;
   float horizMargin = 20.0f;
   float imageQuality = 0.95f; // max is 1.0 (this is for jpeg)
   double rotationRange = 0.7; // this is radians
   BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
   Graphics2D g = (Graphics2D) bufferedImage.getGraphics();

   //Draw an oval
   g.setColor(Color.gray);
   g.fillRect(0, 0, width, height);
   
   // lets make some noisey circles
   g.setColor(circleColor);
   for ( int i = 0; i < circlesToDraw; i++ ) {
      int circleRadius = (int) (Math.random() * height / 2.0);
      int circleX = (int) (Math.random() * width - circleRadius);
      int circleY = (int) (Math.random() * height - circleRadius);
      g.drawOval(circleX, circleY, circleRadius * 2, circleRadius * 2);
   }
   
   g.setColor(textColor);
   g.setFont(textFont);
   
   FontMetrics fontMetrics = g.getFontMetrics();
   int maxAdvance = fontMetrics.getMaxAdvance();
   int fontHeight = fontMetrics.getHeight();

// i removed 1 and l and i because there are confusing to users...
// Z, z, and N also get confusing when rotated
// 0, O, and o are also confusing...
// lowercase G looks a lot like a 9 so i killed it
// this should ideally be done for every language...
// i like controlling the characters though because it helps prevent confusion
//String elegibleChars = "ABCDEFGHJKLMPQRSTUVWXYabcdefhjkmnpqrstuvwxy23456789";
//char[] chars = elegibleChars.toCharArray();

   float spaceForLetters = -horizMargin * 2 + width;
   float spacePerChar = spaceForLetters / (charsToPrint - 1.0f);
   
   AffineTransform transform = g.getTransform();

//StringBuffer finalString = new StringBuffer();

   HttpSession session_actual = request.getSession(true);
   String capstr = (String) session_actual.getAttribute("key");

//for ( int i = 0; i < charsToPrint; i++ ) {
   for ( int i = 0; i < capstr.length(); i++ ) {
      double randomValue = Math.random();
      //int randomIndex = (int) Math.round(randomValue * (chars.length - 1));
      //char characterToShow = chars[randomIndex];
      //finalString.append(characterToShow);
      char characterToShow = capstr.charAt(i);
      // this is a separate canvas used for the character so that
      // we can rotate it independently
      int charImageWidth = maxAdvance * 2;
      int charImageHeight = fontHeight * 2;
      int charWidth = fontMetrics.charWidth(characterToShow);
      int charDim = Math.max(maxAdvance, fontHeight);
      int halfCharDim = (int) (charDim / 2);
      
      BufferedImage charImage = new BufferedImage(charDim, charDim, BufferedImage.TYPE_INT_ARGB);
      Graphics2D charGraphics = charImage.createGraphics();
      charGraphics.translate(halfCharDim, halfCharDim);
      double angle = (Math.random() - 0.5) * rotationRange;
      charGraphics.transform(AffineTransform.getRotateInstance(angle));
      charGraphics.translate(-halfCharDim,-halfCharDim);
      charGraphics.setColor(textColor);
      charGraphics.setFont(textFont);
      
      int charX = (int) (0.5 * charDim - 0.5 * charWidth);
      charGraphics.drawString("" + characterToShow, charX,
      (int) ((charDim - fontMetrics.getAscent())
      / 2 + fontMetrics.getAscent()));
      
      float x = horizMargin + spacePerChar * (i) - charDim / 2.0f;
      int y = (int) ((height - charDim) / 2);
      //System.out.println("x=" + x + " height=" + height + " charDim=" + charDim + " y=" + y + " advance=" + maxAdvance + " fontHeight=" + fontHeight + " ascent=" + fontMetrics.getAscent());
      g.drawImage(charImage, (int) x, y, charDim, charDim, null, null);
      
      charGraphics.dispose();
   }
	 
   //Write the image as a jpg
	Iterator iter = ImageIO.getImageWritersByFormatName("JPG");
	if( iter.hasNext() ) {
      ImageWriter writer = (ImageWriter)iter.next();
      ImageWriteParam iwp = writer.getDefaultWriteParam();
      iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
      iwp.setCompressionQuality(imageQuality);
      writer.setOutput(ImageIO.createImageOutputStream(response.getOutputStream()));
      IIOImage imageIO = new IIOImage(bufferedImage, null, null);
      writer.write(null, imageIO, iwp);
   } else {
      throw new RuntimeException("no encoder found for jsp");
   }

// let's stick the final string in the session
//System.out.println("finalString.toString() :"+finalString.toString());
//request.getSession().setAttribute("captcha", finalString.toString());

   g.dispose();
} catch (IOException ioe) {
   throw new RuntimeException("Unable to build image" , ioe);
}
%>
