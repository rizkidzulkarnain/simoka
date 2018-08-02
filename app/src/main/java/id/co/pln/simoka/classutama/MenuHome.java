package id.co.pln.simoka.classutama;

/**
 * Created by 4741G on 11/02/2018.
 */

public class MenuHome {
    private int id;
    private String name;
    private String desc;
    private int thumbnail;

    public MenuHome() {
    }

    public MenuHome(int id, String name, String desc, int thumbnail) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.thumbnail = thumbnail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
