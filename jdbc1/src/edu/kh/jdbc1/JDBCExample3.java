package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc1.model.vo.Emp;

public class JDBCExample3 {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		
		//부서명을 입력받아 같은 부서에 있는 사원의
		//사원명, 부서명, 급여 조회
		
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		try {
			System.out.print("부서명 입력 : ");
			String input=sc.nextLine();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String type="jdbc:oracle:thin:@"; 
			String ip="localhost";
			String port=":1521"; 
			String sid=":XE";
			String user="kh_khj";
			String pw="kh1234";
			conn=DriverManager.getConnection(type+ip+port+sid,user,pw);
			//Statement가 실행할 SQL
			String sql="SELECT EMP_NAME, NVL(DEPT_TITLE,'부서 없음') AS DEPT_TITLE, SALARY "
					+ "FROM EMPLOYEE LEFT JOIN DEPARTMENT ON(DEPT_CODE=DEPT_ID) "
					+ "WHERE NVL(DEPT_TITLE,'부서 없음') = '"+input+"'";
					//JAVA에서 작성되는 SQL에 문자열 변수를 추가(이어쓰기)할 경우 ''이 누락되지 않도록 신경쓰기!
					//미작성 시 컬럼명으로 인식되어 부적합한 식별자 오류
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			//rs를 List에 옮겨 담기
			List<Emp> list=new ArrayList<>();
			while(rs.next()) {
				//현재 행에 존재하는 컬럼 값 얻어오기
				String empName=rs.getString("EMP_NAME");
				String deptTitle=rs.getString("DEPT_TITLE");
				int salary=rs.getInt("SALARY");
				
				//Emp 객체를 생성해서 컬럼 값 담기
				Emp emp=new Emp(empName,deptTitle,salary);
				list.add(emp);
			}
			if(list.isEmpty()) {
				System.out.println("조회 결과가 없습니다.");
			} else {
				for(Emp emp:list) {
					System.out.println(emp);
				}
			}
		} catch(Exception e) {
			//예외 최상위 부모인 Exception을 catch문에 작성하여 발생하는 모든 예외 처리
		} finally {
			try {
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
