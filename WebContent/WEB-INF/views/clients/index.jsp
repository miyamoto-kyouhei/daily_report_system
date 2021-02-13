<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>取引先&nbsp;一覧</h2>
        <div class="tab_content">
        <table id="client_list">
            <tbody>
                <tr>
                    <th>取引先番号</th>
                    <th>取引先名</th>
                    <th>操作</th>
                </tr>
                <c:forEach var="client" items="${clients}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td><c:out value="${client.id}" /></td>
                        <td><c:out value="${client.name}" /></td>
                        <td>
                            <c:choose>
                                <c:when test="${client.delete_flag == 1}">
                                    （削除済み）
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='/clients/show?id=${client.id}' />">詳細を表示</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${clients_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((clients_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/clients/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        </div>

        <p><a href="<c:url value='/clients/new' />">新規取引先の登録</a></p>

    </c:param>
</c:import>