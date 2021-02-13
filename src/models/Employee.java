package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "employees")
@NamedQueries({
        @NamedQuery(name = Employee.ALL_EMPLOYEES, query = "SELECT e FROM Employee AS e ORDER BY e.id DESC"),
        @NamedQuery(name = Employee.EMPLOYEES_COUNT, query = "SELECT COUNT(e) FROM Employee AS e"),
        @NamedQuery(name = Employee.CHECK_REGISTERED_CODE, query = "SELECT COUNT(e) FROM Employee AS e WHERE e.code = :code"),
        @NamedQuery(name = Employee.CHECK_LOGIN_CODE_AND_PASSWORD, query = "SELECT e FROM Employee AS e WHERE e.delete_flag = 0 AND e.code = :code AND e.password = :pass"),
})
@Entity
public class Employee {

    public static final String ALL_EMPLOYEES = "getAllEmployees";
    public static final String EMPLOYEES_COUNT = "getEmployeesCount";
    public static final String CHECK_REGISTERED_CODE = "checkRegisteredCode";
    public static final String CHECK_LOGIN_CODE_AND_PASSWORD = "checkLoginCodeAndPassword";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * 社員ID
     */
    private Integer id;

    @Column(name = "code", nullable = false, unique = true)
    /**
     * 社員番号
     */
    private String code;

    @Column(name = "name", nullable = false)
    /**
     * 社員名
     */
    private String name;

    @Column(name = "password", length = 64, nullable = false)
    /**
     * ログインパスワード
     */
    private String password;

    @Column(name = "admin_flag", nullable = false)
    /**
     * 管理者権限フラグ
     */
    private Integer admin_flag;

    @Column(name = "created_at", nullable = false)
    /**
     * 登録日時
     */
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    /**
     * 更新日時
     */
    private Timestamp updated_at;

    @Column(name = "delete_flag", nullable = false)
    /**
     * 削除フラグ
     */
    private Integer delete_flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAdmin_flag() {
        return admin_flag;
    }

    public void setAdmin_flag(Integer admin_flag) {
        this.admin_flag = admin_flag;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Integer getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(Integer delete_flag) {
        this.delete_flag = delete_flag;
    }

}