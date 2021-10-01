
import java.io.Serializable;
import java.util.Date;


public class Multimedia implements Serializable{
    private String name;
    private int multId;
    private Date creationDate;
    private String tfLoc;
    private String imgLoc;
   
    public Multimedia(int _id, String _name, Date _creationDate, String _txtfileName, String _imgName){
        this.name = _name;
        this.multId=_id;
        this.creationDate = _creationDate;
        this.tfLoc = _txtfileName;
        this.imgLoc = _imgName;
    }
    
    
    public Multimedia(){
        
    }

    public String getName(){
        return this.name;
    }
    
  
    public String getTextFileName(){
        return this.tfLoc;
    }

 
    public String getImageName(){
        return this.imgLoc;
    }

   
    public int getMultID(){
        return this.multId;
    }

   
    public Date getCreationDate(){
        return this.creationDate;
    }
    
   
    public void setName(String _name){
        this.name = _name;
    }
    
  
    public void setTextFileName(String text){
        this.tfLoc = text;
    }

    public void setCreationDate(Date date){
        this.creationDate = date;
    }

    public void setImageName(String imgName){
        this.imgLoc = imgName;
    }
}
