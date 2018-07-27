package entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Artist.withArtworksCount",
                query = "select count(a) as total from Artist a where size(a.artworkList)>0"),
        @NamedQuery(name = "Artist.ageAverage",
            query = "select avg(a.age)from Artist a ")
})

public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int age;

    @ManyToMany
    @JoinTable(name = "artist_artwork", joinColumns = @JoinColumn(name = "artistid"),
            inverseJoinColumns = {
                    @JoinColumn(name = "artworkid")
            })
    private List<Artwork> artworkList;

    @ManyToOne
    private Address address;

    public List<Artwork> getArtworkList() {
        return artworkList;
    }

    public void setArtworkList(List<Artwork> artworkList) {
        this.artworkList = new ArrayList<>(artworkList);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getName();
    }
}
