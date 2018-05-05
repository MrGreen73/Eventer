package clientChat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;

/**
 * Created by user on 22.04.2018.
 */
@Entity
public class EventApp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer maxPeople;
    private Integer currentPeople = 1;
    private String name;
    private String decription;
    private String place;
    private ArrayList<String> people = new ArrayList<String>();
    private String message = "";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMaxPeople(Integer maxPeople) {
        this.maxPeople = maxPeople;
    }

    public String getName() {
        return name;
    }

    public String getDecription() {
        return decription;
    }

    public String getPlace() {
        return place;
    }

    public ArrayList<String> getPeople() {
        return people;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setCurrentPeople(Integer currentPeople) {
        this.currentPeople = currentPeople;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public void addPeople(String people) {
        this.people.add(people);
    }
}
