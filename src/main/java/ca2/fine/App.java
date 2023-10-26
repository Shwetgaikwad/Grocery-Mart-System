package ca2.fine;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.util.List;
import java.util.Scanner;

public class App {
    private static SessionFactory sessionFactory;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

        while (true) {
            System.out.println("Grocery Store Menu:");
            System.out.println("1. Add grocery");
            System.out.println("2. View grocery");
            System.out.println("3. Delete grocery");
            System.out.println("4. Search grocery by price (HQL)");
            System.out.println("5. Search grocery by Criteria (HCQL)");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    addgrocery();
                    break;
                case 2:
                    viewgrocery();
                    break;
                case 3:
                    deletegrocery();
                    break;
                case 4:
                    searchgroceryByHQL();
                    break;
                case 5:
                    searchgroceryByCriteria();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    sessionFactory.close(); 
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private static void addgrocery() {
        System.out.print("Enter grocery name: ");
        String gname = scanner.nextLine();
        System.out.print("Enter grocery price: ");
        int price = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter grocery quantity: ");
        String quantity = scanner.nextLine();

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            grocery newgrocery = new grocery(gname, price,quantity);
            session.save(newgrocery);

            transaction.commit();
            System.out.println("grocery added successfully!");
        } catch (Exception e) {
            System.err.println("Error adding grocery: " + e.getMessage());
        }
    }

    private static void viewgrocery() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

          
            Query<grocery> query = session.createQuery("FROM grocery", grocery.class);
            List<grocery> grocery = query.getResultList();

            session.getTransaction().commit();

            System.out.println("grocery in the system:");
            for (grocery grocery1 : grocery) {
                System.out.println(grocery1);
            }
        } catch (Exception e) {
            System.err.println("Error viewing grocery: " + e.getMessage());
        }
    }

    private static void deletegrocery() {
        System.out.print("Enter the ID of the grocery to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

          
            grocery grocery = session.get(grocery.class, id);

            if (grocery != null) {
                session.delete(grocery);
                transaction.commit();
                System.out.println("grocery deleted successfully!");
            } else {
                System.out.println("grocery not found.");
            }
        } catch (Exception e) {
            System.err.println("Error deleting grocery: " + e.getMessage());
        }
    }

    private static void searchgroceryByHQL() {
        System.out.print("Enter the price to search for (HQL): ");
        int searchprice = scanner.nextInt();
        scanner.nextLine(); 

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

           
            Query<grocery> query = session.createQuery("FROM grocery WHERE price = :searchprice", grocery.class);
            query.setParameter("searchprice", searchprice);
            List<grocery> grocery = query.getResultList();

            session.getTransaction().commit();

            if (!grocery.isEmpty()) {
                System.out.println("grocery with price " + searchprice + " (HQL):");
                for (grocery grocery1 : grocery) {
                    System.out.println(grocery1);
                }
            } else {
                System.out.println("No grocery found with price " + searchprice + " (HQL)");
            }
        } catch (Exception e) {
            System.err.println("Error searching grocery by HQL: " + e.getMessage());
        }
    }

    private static void searchgroceryByCriteria() {
        System.out.println("Search grocery by Criteria (HCQL):");
        System.out.print("Enter minimum price: ");
        int minprice = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter maximum price: ");
        int maxprice = scanner.nextInt();
        scanner.nextLine(); 

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

          
            Criteria criteria = session.createCriteria(grocery.class);
            criteria.add(org.hibernate.criterion.Restrictions.between("age", minprice, maxprice));
            List<grocery> grocery = criteria.list();

            session.getTransaction().commit();

            if (!grocery.isEmpty()) {
                System.out.println("grocery within price range " + minprice + " - " + maxprice + " (HCQL):");
                for (grocery grocery1 : grocery) {
                    System.out.println(grocery1);
                }
            } else {
                System.out.println("No grocery found within the specified age range (HCQL).");
            }
        } catch (Exception e) {
            System.err.println("Error searching grocery by HCQL: " + e.getMessage());
        }
    }
}
