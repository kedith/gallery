import entity.Address;
import entity.Artist;
import entity.Artwork;
import entity.Gallery;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("ex_gallery");
        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        populateTables(em);

        transaction.commit();

        transaction.begin();
        Query artworkNamedQuery = em.createNamedQuery("Artwork.moreArtists");
        int artistNumber = 1;
        artworkNamedQuery.setParameter("number",artistNumber);
        List<Artwork> artworkList = artworkNamedQuery.getResultList();

        System.out.println("Artworks which have more than " + artistNumber + " artist(s)");
        printArtworks(artworkList);

        transaction.commit();

        transaction.begin();
        Query artworksByPrice = em.createNamedQuery("Artwork.findAllOrderedByPrice");
        artworkList = artworksByPrice.getResultList();
        System.out.println("Artworks ordered by price");
        printArtworks(artworkList);

        transaction.commit();

        transaction.begin();
        Query artistWithArtworksCountQuery = em.createNamedQuery("Artist.withArtworksCount");
        Long artistsCount = (Long)artistWithArtworksCountQuery.getSingleResult();
        System.out.println("The number of the artist with at least one artwork: " + artistsCount);
        transaction.commit();

        transaction.begin();
        Query artistsAgeAvg = em.createNamedQuery("Artist.ageAverage");
        Double ageAvg = (Double) artistsAgeAvg.getSingleResult();
        System.out.println("Age average of artists: " + ageAvg);
        factory.close();
    }

    public static void printArtworks(List<Artwork> artworkList){
        for(Artwork aw : artworkList){
            System.out.print(aw.getTitle() + " " + aw.getPrice());
            System.out.println();
        }
        System.out.println();
    }
    public static void populateTables(EntityManager em){
        Address ad = new Address();
        ad.setCity("city");
        ad.setStreet("street");
        ad.setNumber(2);
        em.persist(ad);

        Artwork artwork1 = new Artwork();
        artwork1.setTitle("artw");
        artwork1.setPrice(333);
        em.persist(artwork1);

        Artwork artwork2 = new Artwork();
        artwork2.setTitle("artw2");
        artwork2.setPrice(222);
        em.persist(artwork2);

        List<Artwork> artworkList = new ArrayList<>();
        artworkList.add(artwork1);
        artworkList.add(artwork2);

        Artist artist1 = new Artist();
        artist1.setName("aritst1");
        artist1.setAge(22);
        artist1.setArtworkList(artworkList);
        artist1.setAddress(ad);
        em.persist(artist1);

        Artist artist2 = new Artist();
        artist2.setName("aritst2");
        artist2.setAge(23);
        artist2.setArtworkList(artworkList.subList(0,1));
        artist2.setAddress(ad);
        em.persist(artist2);

        Artist artist3 = new Artist();
        artist3.setName("aritst3");
        artist3.setAge(24);
        artist3.setAddress(ad);
        em.persist(artist3);

        Address address2 = new Address();
        address2.setCity("city2");
        address2.setStreet("street2");
        address2.setNumber(33);
        em.persist(address2);

        Gallery gallery = new Gallery();
        gallery.setName("gallery");
        gallery.setAddress(address2);
        gallery.setArtworkList(artworkList);
        em.persist(gallery);
    }
}
