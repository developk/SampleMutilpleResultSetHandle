import db.DBManager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;

public class Main {

    public static void main(String[] args) {

        String query = "{CALL BMS_DB.USP_USR_LOGIN(?, ?, ?, ?, ?)}";

        ResultSet rs = null;

        try {

            Connection conn = DBManager.getConnection();
            CallableStatement callableStatement = conn.prepareCall(query);

            callableStatement.setString(1, "erst");
            callableStatement.setString(2, "8157");
            callableStatement.setString(3, "1");
            callableStatement.setString(4, "127.0.0.1");
            callableStatement.registerOutParameter(5, Types.INTEGER);

            boolean isResultSet = callableStatement.execute();

            int result = callableStatement.getInt(5);
            System.out.println("@RESULT = " + result);

            int count = 0;
            while(true) {
                if (isResultSet) {
                    rs = callableStatement.getResultSet();
                    System.out.println("will printed resetSet index: " + count);
                    switch(count) {
                        case 0:
                            while(rs.next()) {
                                System.out.println("USR_ID: " + rs.getString("USR_ID"));
                                System.out.println("USR_NM: " + rs.getString("USR_NM"));
                                System.out.println("CORP_CD: " + rs.getString("CORP_CD"));
                                System.out.println("CORP_NM: " + rs.getString("CORP_NM"));
                                System.out.println("CORP_DV: " + rs.getString("CORP_DV"));
                            }
                            break;
                        case 1:
                            while(rs.next()) {
                                System.out.println("MENU1_CD: " + rs.getString("MENU1_CD"));
                                System.out.println("MENU1_NM: " + rs.getString("MENU1_NM"));
                                System.out.println("MENU2_CD: " + rs.getString("MENU2_CD"));
                                System.out.println("MENU2_NM: " + rs.getString("MENU2_NM"));
                                System.out.println("URL: " + rs.getString("URL"));
                                System.out.println("VIEW_YN: " + rs.getString("VIEW_YN"));
                                System.out.println("WORK_YN: " + rs.getString("WORK_YN"));
                            }
                            break;
                    }

                    rs.close();
                } else {
                    if (callableStatement.getUpdateCount() == -1) {
                        break;
                    }
                }

                count++;
                isResultSet = callableStatement.getMoreResults();
            }

            callableStatement.close();
            conn.close();

        } catch(Exception e) {
            e.printStackTrace();
        }


    }
}
