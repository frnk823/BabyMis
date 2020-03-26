public class Baby {
    private String num;
    private String adress;
    private String postcode;
    private String mother;
    private String father;
    private String sex;
    private float weight;
    private String nurse;
    private String birth;


    public Baby(){
    }

    public Baby(String num, String adress,String postcode,String mother,String father,String sex,float weight,String nurse,String birth){
        this.num = num;
        this.adress = adress;
        this.postcode = postcode;
        this.mother = mother;
        this.father = father;
        this.sex = sex;
        this.weight = weight;
        this.nurse = nurse;
        this.birth = birth;
    }

    public void setNum(String num){
        this.num = num;
    }

    public void setAdress(String adress){
        this.adress = adress;
    }

    public void setPostcode(String postcode){
        this.postcode = postcode;
    }

    public void setMother(String mother){
        this.mother = mother;
    }

    public void setFather(String father){
        this.father = father;
    }

    public void setSex(String sex){
        this.sex = sex;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }

    public void setNurse(String nurse){
        this.nurse = nurse;
    }

    public void setBirth(String birth){
        this.birth = birth;
    }

    public String getNum(){
        return num;
    }

    public String getAdress(){
        return adress;
    }

    public String getPostcode(){
        return postcode;
    }

    public String getFather(){
        return father;
    }

    public String getMother(){
        return mother;
    }

    public String getSex(){
        return sex;
    }

    public float getWeight(){
        return weight;
    }

    public String getNurse(){
        return nurse;
    }

    public String getBirth() {
        return birth;
    }
}