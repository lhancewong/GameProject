/**
	This is a template for a Java file.
	
	@author Wilbert Meinard L. Chen (201153)
    @author Lhance Christian S. Wong (205467)
	@version May 31, 2021
**/

/*
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
*/

public class Hitbox {
    private double xPos;
    private double yPos;
    private double width;
    private double height;


    public Hitbox(double xPos, double yPos, double width, double height){
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }

    public double getX(){
        return xPos;
    }

    public double getY(){
        return yPos;
    }

    public double getWidth(){
        return width;
    }

    public double getHeight(){
        return height;
    }
}
