
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Catalogue implements Serializable{
    private List<Multimedia> mulList;
    private String catName;

    /**
     *
     * @param _id
     */
    public Catalogue(String _id){
        this.catName = _id;
        this.mulList = new ArrayList<>();
    }


    /**
     *
     * @return
     */
    public String getCatName(){
        return this.catName;
    }

    /**
     *
     * @return
     */
    public List<Multimedia> getMultimediaList(){
        return this.mulList;
    }

    /**
     *
     * @param _id
     */
    public void setCatName(String _id){
        this.catName = _id;
    }

    /**
     *
     * @param _list
     */
    public void setMultList(List<Multimedia> _list){
        this.mulList = _list;
    }
}
