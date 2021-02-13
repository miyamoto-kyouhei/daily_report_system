<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>
<label for="report_date">日付</label><br />
<input type="date" name="report_date" value="<fmt:formatDate value='${report.report_date}' pattern='yyyy-MM-dd' />" />
<br /><br />

<label for="name">氏名</label><br />
<c:out value="${sessionScope.login_employee.name}" />
<br /><br />

<label for="title">タイトル</label><br />
<input type="text" name="title" value="${report.title}" />
<br /><br />

<label for="content">内容</label><br />
<textarea name="content" rows="10" cols="50">${report.content}</textarea>
<br /><br />

<table id="sales_call_list">
  <tbody>
    <tr>
      <th>取引先</th>
      <th>訪問時間</th>
      <th>商談内容</th>
    </tr>
    <c:set var="listSize" value="${report.salesCallsList.size()}" />
    <c:choose>
      <c:when test="${listSize > 0}">
        <c:forEach begin="0" end="${listSize -1}" varStatus="status">
          <tr>
            <td>
              <select name="clients${status.index}">
                <option value="0">--------</option>
                <c:forEach var="client" items="${clients}" varStatus="clStatus">
                  <option value="${client.id}"
                  <c:if test="${client.id == report.salesCallsList[status.index].client.id}">
                    selected
                  </c:if>>
                  <c:out value="${client.name}" /></option>
                </c:forEach>
              </select>
            </td>
            <td>
              <input type="time" name="visit_time${status.index}" value="${report.salesCallsList[status.index].visit_time}" />
            </td>
            <td>
              <textarea name="sales_report${status.index}" rows="1" cols="50" ><c:out value="${report.salesCallsList[status.index].sales_report}"></c:out></textarea>
            </td>
          </tr>
        </c:forEach>
        <c:if test="${listSize < 3}">
        <c:forEach begin="${listSize}" end="2" varStatus="status">
          <tr>
            <td>
              <select name="clients${status.index}">
                <option value="0">--------</option>
                <c:forEach var="client" items="${clients}" varStatus="clStatus">
                  <option value="${client.id}"><c:out value="${client.name}" /></option>
                </c:forEach>
              </select>
            </td>
            <td>
              <input type="time" name="visit_time${status.index}"  />
            </td>
            <td>
              <textarea name="sales_report${status.index}" rows="1" cols="50"></textarea>
            </td>
          </tr>
        </c:forEach>
        </c:if>

      </c:when>

      <c:otherwise>
        <c:forEach begin="0" end="2" varStatus="status">
          <tr>
            <td>
              <div class="select-wrap">
                  <select name="clients${status.index}">
                    <option value="0">--------</option>
                    <c:forEach var="client" items="${clients}" varStatus="clStatus">
                      <option value="${client.id}"><c:out value="${client.name}" /></option>
                    </c:forEach>
                  </select>
              </div>
            </td>
            <td>
              <input type="time" name="visit_time${status.index}"  />
            </td>
            <td>
              <textarea name="sales_report${status.index}" rows="1" cols="50"></textarea>
            </td>
          </tr>
        </c:forEach>
      </c:otherwise>
    </c:choose>


  </tbody>
</table>
<br /><br />

<label for="attendance_time">出勤時間</label><br />
<c:choose>
    <c:when test="${report.attendance_time != null}">
        <input type="time" name="attendance_time" value="<tags:localTime time="${report.attendance_time}" pattern="HH:mm"/>" />
    </c:when>
    <c:otherwise>
        <input type="time" name="attendance_time" value="09:00" />
    </c:otherwise>
</c:choose>
<br /><br />

<label for="leave_time">退勤時間</label><br />
<c:choose>
    <c:when test="${report.leave_time != null}">
        <input type="time" name="leave_time" value="<tags:localTime time="${report.leave_time}" pattern="HH:mm"/>" />
    </c:when>
    <c:otherwise>
        <input type="time" name="leave_time" value="18:00" />
    </c:otherwise>
</c:choose>

<br /><br />


<input type="hidden" name="_token" value="${_token}" />
<button type="submit">投稿</button>
