package mx.com.aries.entities;

public class Scene {
    private int id;
    private String name;
    private String description;
    private double x;
    private double y;

    private int idUser;
    private String previewImageUrl;

    private String image;

    public Scene() {
        this.id = -1;
        this.name = null;
        this.description = null;

        this.previewImageUrl = null;
        this.idUser = -1;

        this.image = null;
    }

    public Scene(int id, String name, String description, double x, double y, int idUser, String previewImageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.x = x;
        this.y = y;
        this.previewImageUrl = previewImageUrl;
        this.idUser = idUser;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getPreviewImageUrl() {
        return previewImageUrl;
    }

    public void setPreviewImageUrl(String previewImageUrl) {
        this.previewImageUrl = previewImageUrl;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
