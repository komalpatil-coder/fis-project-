
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Catalogue implements Serializable{
    private List<Multimedia> mulList;
    private String catName;

   
    public Catalogue(String _id){
        this.catName = _id;
        this.mulList = new ArrayList<>();
    }


    public String getCatName(){
        return this.catName;
    }

    public List<Multimedia> getMultimediaList(){
        return this.mulList;
    }

    public void setCatName(String _id){
        this.catName = _id;
    }

    
    public void setMultList(List<Multimedia> _list){
        this.mulList = _list;
    }
}
