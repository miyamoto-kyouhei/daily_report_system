package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "favorites")
@NamedQueries({
        @NamedQuery(name = Favorite.MY_REPORT_All_FAVORITES, query = "SELECT f FROM Favorite AS f WHERE f.report = :report ORDER BY f.id ASC"),
        @NamedQuery(name = Favorite.MY_REPORT_All_FAVORITES_COUNT, query = "SELECT COUNT(f) FROM Favorite AS f WHERE f.report = :report"),
        @NamedQuery(name = Favorite.MY_FAVORITES, query = "SELECT f FROM Favorite AS f WHERE f.employee = :employee ORDER BY f.id ASC"),
        @NamedQuery(name = Favorite.MY_FAVORITES_COUNT, query = "SELECT COUNT(f) FROM Favorite AS f WHERE f.employee = :employee"),
        @NamedQuery(name = Favorite.MY_FAVORITED_CHECK, query = "SELECT f FROM Favorite AS f WHERE f.employee = :employee AND f.report = :report"),
        @NamedQuery(name = Favorite.MY_FAVORITED_CHECK_COUNT, query = "SELECT COUNT(f) FROM Favorite AS f WHERE f.employee = :employee AND f.report = :report"),
        @NamedQuery(name = Favorite.FIND_BY_FAVORITES, query = "SELECT f.report FROM Favorite AS f WHERE f.employee = :employee ORDER BY f.report.report_date DESC"),
        @NamedQuery(name = Favorite.FIND_BY_FAVORITES_COUNT, query = "SELECT COUNT(f.report) FROM Favorite AS f WHERE f.employee = :employee"),
})
@Entity
public class Favorite {

    public static final String MY_REPORT_All_FAVORITES = "getMyReportAllFavorites";
    public static final String MY_REPORT_All_FAVORITES_COUNT = "getMyReportAllFavoritesCount";
    public static final String MY_FAVORITES = "getMyFavorites";
    public static final String MY_FAVORITES_COUNT = "getMyFavoritesCount";
    public static final String MY_FAVORITED_CHECK = "getMyFavoritedCheck";
    public static final String MY_FAVORITED_CHECK_COUNT = "getMyFavoritedCheckCount";
    public static final String FIND_BY_FAVORITES = "getConditionFavorites";
    public static final String FIND_BY_FAVORITES_COUNT = "getConditionFavoritesCount";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * いいねID
     */
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    /**
     * いいねした人
     */
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "report_id", referencedColumnName = "id", nullable = false)
    /**
     * いいねした日報
     */
    private Report report;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

}
