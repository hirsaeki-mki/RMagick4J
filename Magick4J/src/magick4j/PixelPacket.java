package magick4j;

import java.awt.Color;

/**
 * TODO Or is Color good enough? Do we support depth of 16 or 32??? Probably not
 * at first.
 */

/*
 * WARNING: 
 * In RMagick, an alpha value of 0 is OpaqueOpacity, and 255 is TransparentOpacity.
 * In java.awt.Color 0 is transparent, and 255 opaque.
 * 
 */

public class PixelPacket {

    private int blue;
    private int green;
    private int opacity;
    private int red;

    public PixelPacket() {
        this(0, 0, 0, 0);
    }

    
    public PixelPacket(double red, double green, double blue){
        this(red,green,blue,Constants.OpaqueOpacity);
    }

    public PixelPacket(int red, int green, int blue) {
        this(red, green, blue, Constants.OpaqueOpacity);
    }

    public PixelPacket(double red, double green, double blue, double alpha){
        this.setRed((int) Math.round(red*Constants.QuantumRange));
        this.setGreen((int) Math.round(green*Constants.QuantumRange));
        this.setBlue((int) Math.round(blue*Constants.QuantumRange));
        this.setOpacity((int) Math.round(alpha*Constants.QuantumRange));
    }

    public PixelPacket(int red, int green, int blue, int opacity) {
        this.setRed(red);
        this.setGreen(green);
        this.setBlue(blue);
        this.setOpacity(opacity);
    }

	public static double calculateIntensity(double red, double green, double blue){
		return 0.299*red+0.587*green+0.114*blue;
	}

	public static double calculateIntensity(double[] p, int ini){
		return calculateIntensity(p[ini],p[ini+1],p[ini+2]);
	}

    @Override
    public Object clone(){
        return new PixelPacket(
                                this.red,
                                this.green,
                                this.blue,
                                this.opacity
                                );
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof PixelPacket){
            PixelPacket pixel = (PixelPacket) o;
            return  this.getRed()     == pixel.getRed()   &&
                    this.getGreen()   == pixel.getGreen() &&
                    this.getBlue()    == pixel.getBlue()  &&
                    this.getOpacity() == pixel.getOpacity();
        }else{
            return true;
        }
    }

    public int getBlue() {
        return blue;
    }

    public int getGreen() {
        return green;
    }

	public int getIntensity(){
		return (int) Math.round(calculateIntensity(this.red, this.green, this.blue));
	}

    public int getOpacity() {
        return opacity;
    }

    public int getRed() {
        return red;
    }

    public void setBlue(int blue) {
        this.blue = blue%256;
        // Serabe: Don't touch. Java defined -56%256 as 
        if(this.blue < 0) this.blue += 256;
    }

    public void setGreen(int green) {
        this.green = green%256;
        // Serabe: Don't touch. Java defined -56%256 as 
        if(this.green < 0) this.green += 256;
    }

    public void setOpacity(int opacity) {
        this.opacity = opacity%256;
        // Serabe: Don't touch. Java defined -56%256 as 
        if(this.opacity < 0) this.opacity += 256;
    }

    public void setRed(int red) {
        this.red = red%256;
        // Serabe: Don't touch. Java defined -56%256 as 
        if(this.red < 0) this.red += 256;
    }

    public Color toColor() {
        return new Color( red, green, blue, 255-opacity);// See warning above.
    }
    
    public double[] toDoubleArray(){
        double[] data = new double[4];
        data[0] = this.getRed();
        data[1] = this.getGreen();
        data[2] = this.getBlue();
        data[3] = Constants.QuantumRange-this.getOpacity(); // See warning above.
        return data;
    }
    
    public Color toOpaqueColor(){
        return new Color(red, green, blue);
    }
    
    @Override
    public String toString(){
        String name = ColorDatabase.getName(this);
        if(name == null){
            name = "rgb("+ this.getRed()   + ","+
                           this.getGreen() + ","+
                           this.getBlue()  + ")";
        }
        return name;
    }
}
