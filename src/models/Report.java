package models;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name = "reports")
@NamedQueries({
        @NamedQuery(name = Report.ALL_REPORTS, query = "SELECT r FROM Report AS r ORDER BY r.id DESC"),
        @NamedQuery(name = Report.REPORTS_COUNT, query = "SELECT COUNT(r) FROM Report AS r"),
        @NamedQuery(name = Report.FIND_BY_EMPLOYEES, query = "SELECT r FROM Report AS r WHERE r.employee = :employee ORDER BY r.id DESC"),
        @NamedQuery(name = Report.FIND_BY_EMPLOYEES_COUNT, query = "SELECT COUNT(r) FROM Report AS r WHERE r.employee = :employee"),
        @NamedQuery(name = Report.FIND_BY_FOLLOWS, query = "SELECT r FROM Report AS r WHERE r.employee IN (:el) ORDER BY r.created_at DESC"),
        @NamedQuery(name = Report.FIND_BY_FOLLOWS_COUNT, query = "SELECT COUNT(r) FROM Report AS r WHERE r.employee IN (:el) ORDER BY r.created_at DESC"),
})
@Entity
public class Report {

    public static final String ALL_REPORTS = "getAllReports";
    public static final String REPORTS_COUNT = "getReportsCount";
    public static final String FIND_BY_EMPLOYEES = "getMyAllReports";
    public static final String FIND_BY_EMPLOYEES_COUNT = "getMyReportsCount";
    public static final String FIND_BY_DEPARTMENTS = "getConditionDepartmentReports";
    public static final String FIND_BY_DEPARTMENTS_COUNT = "getConditionDepartmentReportsCount";
    public static final String FIND_BY_FOLLOWS = "getConditionFollowReports";
    public static final String FIND_BY_FOLLOWS_COUNT = "getConditionFollowReportsCount";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * 日報ID
     */
    private Integer id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "report_id", referencedColumnName = "id")
    /**
     * 営業訪問リスト
     */
    private List<SalesCalls> salesCallsList;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    /**
     * 社員ID
     */
    private Employee employee;

    @Column(name = "report_date", nullable = false)
    /**
     * 日報日付
     */
    private Date report_date;

    @Column(name = "title", length = 255, nullable = false)
    /**
     * タイトル
     */
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    /**
     * 日報の内容
     */
    private String content;

    @Column(name = "attendance_time")
    /**
     * 出勤時間
     */
    private LocalTime attendance_time;

    @Column(name = "leave_time")
    /**
     * 退勤時間
     */
    private LocalTime leave_time;

//    @OneToOne
//    @JoinColumn(name = "format_id", referencedColumnName = "id", nullable = false)
//    /**
//     * 書式ID
//     */
//    private Format format;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<SalesCalls> getSalesCallsList() {
        return salesCallsList;
    }

    public void setSalesCallsList(List<SalesCalls> salesCallsList) {

        if (this.salesCallsList == null) {
            this.salesCallsList = salesCallsList;
        } else {
            this.salesCallsList.retainAll(salesCallsList);
            this.salesCallsList.addAll(salesCallsList);
        }

    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getReport_date() {
        return report_date;
    }

    public void setReport_date(Date report_date) {
        this.report_date = report_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalTime getAttendance_time() {
        return attendance_time;
    }

    public void setAttendance_time(LocalTime attendance_time) {
        this.attendance_time = attendance_time;
    }

    public LocalTime getLeave_time() {
        return leave_time;
    }

    public void setLeave_time(LocalTime leave_time) {
        this.leave_time = leave_time;
    }

//    public Format getFormat() {
//        return format;
//    }
//
//    public void setFormat(Format format) {
//        this.format = format;
//    }

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
}