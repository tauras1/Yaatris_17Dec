package com.example.yaatris;

public class AdventureData {
    public String imageName;
    public String imageURL;
    public String adventureName;
    public String location;
    public String checkpoints;
    public String from;
    public String to;
    public String desc;
    public String price;
    public String cmail;




    public AdventureData(){}

    public AdventureData(String cmail, String adventureName, String location, String checkpoints, String from, String to, String price, String desc, String name, String url) {
        this.cmail = cmail;
        this.adventureName = adventureName;
        this.location = location;
        this.checkpoints = checkpoints;
        this.from = from;
        this.to = to;
        this.desc = desc;
        this.price = price;
        this.imageName = name;
        this.imageURL = url;
    }

    public String getImageName() {
        return imageName;
    }
    public String getImageURL() {
        return imageURL;
    }

}
