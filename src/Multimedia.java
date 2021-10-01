
import java.io.Serializable;
import java.util.Date;


public class Multimedia implements Serializable{
    private String name;
    private int multId;
    private Date creationDate;
    private String tfLoc;
    private String imgLoc;
    /**
     * Multimedia Constructor
     * @param _id id of multimedia
     * @param _name name of multimedia
     * @param _creationDate creation date of multimedia
     * @param _txtfileName name of text file of multimedia
     * @param _imgName name of image file of multimedia
     */
    public Multimedia(int _id, String _name, Date _creationDate, String _txtfileName, String _imgName){
        this.name = _name;
        this.multId=_id;
        this.creationDate = _creationDate;
        this.tfLoc = _txtfileName;
        this.imgLoc = _imgName;
    }
    
    /**
     * blank constructor 
     */
    public Multimedia(){
        
    }

    /**
     * getter for name
     * @return name of multimedia
     */
    public String getName(){
        return this.name;
    }
    
    /**
     *getter for text file name
     * @return name of text file in multimedia
     */
    public String getTextFileName(){
        return this.tfLoc;
    }

    /**
     *getter for image file name
     * @return name of image file in multimedia
     */
    public String getImageName(){
        return this.imgLoc;
    }

    /**
     *getter for multimedia id
     * @return integer of multimedia id
     */
    public int getMultID(){
        return this.multId;
    }

    /**
     * getter for date of creation
     * @return date multimedia was created
     */
    public Date getCreationDate(){
        return this.creationDate;
    }
    
    /**
     * setter for name
     * @param _name name of multimedia
     */
    public void setName(String _name){
        this.name = _name;
    }
    
    /**
     *setter for text file name
     * @param text name/path of text file
     */
    public void setTextFileName(String text){
        this.tfLoc = text;
    }

    /**
     *setter for date of creation
     * @param date date of creation for multimedia
     */
    public void setCreationDate(Date date){
        this.creationDate = date;
    }

    /**
     * setter for image name
     * @param imgName name/path of image file
     */
    public void setImageName(String imgName){
        this.imgLoc = imgName;
    }
}
