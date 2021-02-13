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

@Table(name = "follows")
@NamedQueries({
        @NamedQuery(name = Follow.MY_FOLLOWED_CHECK, query = "SELECT f FROM Follow AS f WHERE f.follow_employee = :follow_employee AND f.follower_employee = :follower_employee"),
        @NamedQuery(name = Follow.MY_FOLLOWED_CHECK_COUNT, query = "SELECT COUNT(f) FROM Follow AS f WHERE f.follow_employee = :follow_employee AND f.follower_employee = :follower_employee"),
        @NamedQuery(name = Follow.FIND_BY_FOLLOWS, query = "SELECT f.follower_employee FROM Follow AS f WHERE f.follow_employee = :employee"),
})
@Entity
public class Follow {

    public static final String MY_FOLLOWED_CHECK = "getMyFollowedCheck";
    public static final String MY_FOLLOWED_CHECK_COUNT = "getMyFollowedCheckCount";
    public static final String FIND_BY_FOLLOWS = "getConditionFollows";


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * フォローID
     */
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "following_id", referencedColumnName = "id", nullable = false)
    /**
     * フォローした人
     */
    private Employee follow_employee;

    @ManyToOne
    @JoinColumn(name = "follower_id", referencedColumnName = "id", nullable = false)
    /**
     * フォローされた人
     */
    private Employee follower_employee;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getFollow_employee() {
        return follow_employee;
    }

    public void setFollow_employee(Employee follow_employee) {
        this.follow_employee = follow_employee;
    }

    public Employee getFollower_employee() {
        return follower_employee;
    }

    public void setFollower_employee(Employee follower_employee) {
        this.follower_employee = follower_employee;
    }

}
