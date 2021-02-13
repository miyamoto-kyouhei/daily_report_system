<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script>
window.onload = function() {
    // いいねボタンを押下
    $('a#favorite_button').on('click',function() {

        // HTMLでの送信をキャンセル
        event.preventDefault();

        // 各フィールドから値を取得してJSONデータを作成
        var data = {
            report_id : $("#report_id").val(),
            _token : $("#_token").val(),
            favorite_status : $("#favorite_status").val()
        };

        // 通信実行
        $.ajax({
            type : "post", // method = "POST"
            url : "../reports/favorite", // POST送信先のURL
            dataType : "json",
            data : data, // レスポンスをJSONとしてパースする
            success : function(result) {

                // cssを変更
                if(result.status == 0){
                    $('#favorite_button').removeClass('btn_on');
                    $('#favorite_button').addClass('btn');

                }else{
                    $('#favorite_button').removeClass('btn');
                    $('#favorite_button').addClass('btn_on');
                }

                // いいね件数を変更
                $("#favorite_status").val(result.status);
                $("#number").text(result.count);

            },
            error : function() { // HTTPエラー時
                alert("Server Error. Pleasy try again later.");
            },
            complete : function() { // 成功・失敗に関わらず通信が終了した際の処理
                }
            });
        });
        // フォローボタンを押下
        $('a#follow_button').on('click',function() {

            // HTMLでの送信をキャンセル
            event.preventDefault();

            // 各フィールドから値を取得してJSONデータを作成
            var data = {
                report_id : $("#report_id").val(),
                _token : $("#_token").val(),
                follow_status : $("#follow_status").val()
            };

            // 通信実行
            $.ajax({
                type : "post", // method = "POST"
                url : "../reports/follow", // POST送信先のURL
                dataType : "json",
                data : data, // レスポンスをJSONとしてパースする
                success : function(result) {

                    // cssを変更
                    if(result.status == 0){
                        $('#follow_button').removeClass('btn_on');
                        $('#follow_button').addClass('btn');
                        $('#follow_button').text('フォロー');
                    }else{
                        $('#follow_button').removeClass('btn');
                        $('#follow_button').addClass('btn_on');
                        $('#follow_button').text('フォロー済み');
                    }

                    $("#follow_status").val(result.status);

                },
                error : function() { // HTTPエラー時
                    alert("Server Error. Pleasy try again later.");
                },
                complete : function() { // 成功・失敗に関わらず通信が終了した際の処理
                }
            });
        });
    };
</script>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${report != null}">
                <h2>日報&nbsp;&nbsp;詳細ページ</h2>

                <table>
                    <tbody>
                        <tr>
                            <th>氏名</th>
                            <td>
                              <c:out value="${report.employee.name}" />
                              <div class="follow">
                                <c:choose>
                                  <c:when test="${followed_status == 0}">
                                    <a href="" class="btn" id="follow_button">フォロー</a>
                                  </c:when>
                                  <c:otherwise>
                                    <a href="" class="btn_on" id="follow_button">フォロー済み</a>
                                  </c:otherwise>
                                </c:choose>

                              </div>
                            </td>
                        </tr>
                        <tr>
                            <th>日付</th>
                            <td><fmt:formatDate value="${report.report_date}"
                                    pattern="yyyy-MM-dd" /></td>
                        </tr>
                        <tr>
                            <th>内容</th>
                            <td><pre><c:out value="${report.content}" /></pre></td>
                        </tr>
                        <c:choose>
                            <c:when test="${report.salesCallsList != null}">
                                <c:forEach var="salesCalls" items="${report.salesCallsList}"
                                    varStatus="status">
                                    <tr>
                                        <th>訪問先<c:out value="${status.count}" /></th>
                                        <td>
                                            <table>
                                                <tr>
                                                    <th>訪問先</th>
                                                    <td><c:out value="${salesCalls.client.name}" /></td>
                                                </tr>
                                                <tr>
                                                    <th>訪問日時</th>
                                                    <td><c:if test="${not empty salesCalls.visit_time}">
                                                            <tags:localTime time="${salesCalls.visit_time}"
                                                                pattern="HH:mm" />
                                                        </c:if></td>
                                                </tr>
                                                <tr>
                                                    <th>商談内容</th>
                                                    <td><pre><c:out value="${salesCalls.sales_report}" /></pre></td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                        </c:choose>
                        <tr>
                            <th>出勤時間</th>
                            <td><c:if test="${not empty report.attendance_time}">
                                    <tags:localTime time="${report.attendance_time}"
                                        pattern="HH:mm" />
                                </c:if></td>
                        </tr>
                        <tr>
                            <th>退勤時間</th>
                            <td><c:if test="${not empty report.leave_time}">
                                    <tags:localTime time="${report.leave_time}" pattern="HH:mm" />
                                </c:if></td>
                        </tr>
                        <tr>
                            <th>登録日時</th>
                            <td><fmt:formatDate value="${report.created_at}"
                                    pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        </tr>
                        <tr>
                            <th>更新日時</th>
                            <td><fmt:formatDate value="${report.updated_at}"
                                    pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        </tr>
                    </tbody>
                </table>

                <c:if test="${sessionScope.login_employee.id == report.employee.id}">
                    <div><p>
                        <a href="<c:url value="/reports/edit?id=${report.id}" />">この日報を編集する</a>
                    </p></div>
                </c:if>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <div><p><a href="<c:url value="/reports/index" />">一覧に戻る</a></p></div>


        <input type="hidden" id="_token" name="_token" value="${_token}" />
        <input type="hidden" id="iine_employee_id" value="${sessionScope.login_employee.id}" />
        <input type="hidden" id="report_employee_id"value="${report.employee.id}" />
        <input type="hidden" id="report_id" value="${report.id}" />
        <input type="hidden" id="favorite_status" value="${favorited_status}" />
        <input type="hidden" id="follow_status" value="${followed_status}" />

        <div class="favorite">
            <c:choose>
                <c:when test="${favorited_status == 0}">
                    <a href="" class="btn" id="favorite_button">いいね</a>
                </c:when>
                <c:otherwise>
                    <a href="" class="btn_on" id="favorite_button">いいね</a>
                </c:otherwise>
            </c:choose>

        </div>
        <div class="balloon">
            <span class="number" id="number"><c:out
                    value="${favorites_count}" /></span>
        </div>

    </c:param>
</c:import>