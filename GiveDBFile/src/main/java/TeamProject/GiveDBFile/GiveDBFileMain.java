package TeamProject.GiveDBFile;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//File Generator to transform brought view from connected database.

public class GiveDBFileMain implements ActionListener {

	private JFrame 				frame;			
	private JTable 				table;			

	private JButton 			btnExcel;
	private JButton 			btnConnect;
	private JButton 			btnSearch;
	private JButton 			btnDefaultPath;
	private JButton 			btnDelete;

	private JTextField 			tfIP;
	private JTextField 			tfPort;
	private JTextField 			tfSid;
	private JTextField 			tfID;
	private JTextField 			tfPWD;
	private JTextField 			tfPath;

	private JLabel 				lbDBMSG;
	private JLabel 				lbSearchMSG;
	private JLabel 				lbExcelMSG;
	private JLabel 				lbSavedPathMSG;
	private JLabel 				lbCurrentID;

	private JList<String> 	  	listColumn;	
	private JScrollPane 	  	scrollPane;
	private JComboBox<String> 	cbbView;
	private JComboBox<String> 	cbbColumn;

	private String 			  	driver = "oracle.jdbc.driver.OracleDriver"; 
	private String 			  	url;
	private Connection 		  	con;
	private String 			  	sql;
	private PreparedStatement 	pstmt;
	private ResultSet 		  	rs;

	private final String 		defaultViewString = "(Select View)";

	private Vector<String> 		allColumnsinView = new Vector<String>();	
	private String 				selectedView;
	private Vector<String> 		selectedColumns = new Vector<String>();
	private ArrayList<String> 	selectedColumnDataTypes = new ArrayList<String>();

	private Vector<String> 		excelHeader = new Vector<String>();
	private Vector<String> 		excelData = new Vector<String>();
	private int 				columnsCount;
	private int 				rowsCount;

	private DefaultListModel<String> dlModelColumn = new DefaultListModel<String>();
	private DefaultTableModel 		 dtModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GiveDBFileMain window = new GiveDBFileMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GiveDBFileMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.menu);
		frame.setBounds(100, 100, 841, 564);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(220, 140, 579, 280);
		frame.getContentPane().add(scrollPane);

		JLabel lbShowSarch = new JLabel("*검색결과");
		lbShowSarch.setForeground(new Color(0, 0, 255));
		lbShowSarch.setHorizontalAlignment(SwingConstants.LEFT);
		lbShowSarch.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lbShowSarch.setBounds(210, 110, 65, 20);
		frame.getContentPane().add(lbShowSarch);

		btnSearch = new JButton("조회");
		btnSearch.setBackground(UIManager.getColor("Button.background"));
		btnSearch.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		btnSearch.setBounds(280, 110, 90, 25);
		frame.getContentPane().add(btnSearch);
		btnSearch.addActionListener(this);

		JLabel lbShowSCondition = new JLabel("*검색조건");
		lbShowSCondition.setForeground(new Color(0, 0, 255));
		lbShowSCondition.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lbShowSCondition.setHorizontalAlignment(SwingConstants.LEFT);
		lbShowSCondition.setBounds(15, 110, 65, 20);
		frame.getContentPane().add(lbShowSCondition);

		JLabel lbShowSelectedView = new JLabel("selectedView :");
		lbShowSelectedView.setHorizontalAlignment(SwingConstants.RIGHT);
		lbShowSelectedView.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lbShowSelectedView.setBounds(15, 140, 60, 20);
		frame.getContentPane().add(lbShowSelectedView);

		cbbView = new JComboBox<String>();
		cbbView.setBounds(80, 140, 110, 20);
		frame.getContentPane().add(cbbView);
		cbbView.addActionListener(this);

		JLabel lbShowSelectColumn = new JLabel("Column :");
		lbShowSelectColumn.setHorizontalAlignment(SwingConstants.RIGHT);
		lbShowSelectColumn.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lbShowSelectColumn.setBounds(15, 165, 60, 20);
		frame.getContentPane().add(lbShowSelectColumn);

		cbbColumn = new JComboBox<String>();
		cbbColumn.setBounds(80, 165, 110, 20);
		frame.getContentPane().add(cbbColumn);
		cbbColumn.addActionListener(this);

		JScrollPane spColumn = new JScrollPane();
		spColumn.setBounds(80, 195, 110, 225);
		frame.getContentPane().add(spColumn);

		listColumn = new JList<String>(dlModelColumn);
		listColumn.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listColumn.setVisibleRowCount(20);
		listColumn.setBackground(new Color(255, 255, 255));
		spColumn.setColumnHeaderView(listColumn);

		btnExcel = new JButton("출력");
		btnExcel.setBackground(UIManager.getColor("Button.background"));
		btnExcel.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		btnExcel.setBounds(90, 440, 90, 25);
		frame.getContentPane().add(btnExcel);
		btnExcel.addActionListener(this);

		JLabel lbShowChoosePath = new JLabel("경로지정 :");
		lbShowChoosePath.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lbShowChoosePath.setHorizontalAlignment(SwingConstants.RIGHT);
		lbShowChoosePath.setBounds(15, 470, 70, 20);
		frame.getContentPane().add(lbShowChoosePath);

		JLabel lbShowConnectDB = new JLabel("*DB연결");
		lbShowConnectDB.setForeground(new Color(0, 0, 255));
		lbShowConnectDB.setHorizontalAlignment(SwingConstants.LEFT);
		lbShowConnectDB.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lbShowConnectDB.setBounds(15, 15, 60, 20);
		frame.getContentPane().add(lbShowConnectDB);

		tfIP = new JTextField();
		tfIP.setText("localhost");
		tfIP.setBounds(155, 45, 120, 20);
		frame.getContentPane().add(tfIP);
		tfIP.setColumns(10);

		JLabel lbShowIP = new JLabel("IP :");
		lbShowIP.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lbShowIP.setHorizontalAlignment(SwingConstants.RIGHT);
		lbShowIP.setBounds(85, 45, 60, 20);
		frame.getContentPane().add(lbShowIP);

		tfPort = new JTextField();
		tfPort.setText("1521");
		tfPort.setColumns(10);
		tfPort.setBounds(365, 45, 120, 20);
		frame.getContentPane().add(tfPort);

		JLabel lbShowPort = new JLabel("PORT :");
		lbShowPort.setHorizontalAlignment(SwingConstants.RIGHT);
		lbShowPort.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lbShowPort.setBounds(295, 45, 60, 20);
		frame.getContentPane().add(lbShowPort);

		tfSid = new JTextField();
		tfSid.setText("XE");
		tfSid.setColumns(10);
		tfSid.setBounds(575, 45, 120, 20);
		frame.getContentPane().add(tfSid);

		JLabel lbShowSid = new JLabel("SID :");
		lbShowSid.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lbShowSid.setHorizontalAlignment(SwingConstants.RIGHT);
		lbShowSid.setBounds(505, 45, 60, 20);
		frame.getContentPane().add(lbShowSid);

		btnConnect = new JButton("연결");
		btnConnect.setBackground(UIManager.getColor("Button.background"));
		btnConnect.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		btnConnect.setBounds(80, 15, 90, 25);
		frame.getContentPane().add(btnConnect);
		btnConnect.addActionListener(this);

		JLabel lbShowID = new JLabel("ID :");
		lbShowID.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lbShowID.setHorizontalAlignment(SwingConstants.RIGHT);
		lbShowID.setBounds(85, 70, 60, 20);
		frame.getContentPane().add(lbShowID);

		tfID = new JTextField();
		tfID.setText("madang");
		tfID.setBounds(155, 70, 120, 20);
		frame.getContentPane().add(tfID);
		tfID.setColumns(10);

		JLabel lbShowPWD = new JLabel("PWD :");
		lbShowPWD.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lbShowPWD.setHorizontalAlignment(SwingConstants.RIGHT);
		lbShowPWD.setBounds(295, 70, 60, 20);
		frame.getContentPane().add(lbShowPWD);

		tfPWD = new JTextField();
		tfPWD.setText("madang");
		tfPWD.setColumns(10);
		tfPWD.setBounds(365, 70, 120, 20);
		frame.getContentPane().add(tfPWD);

		lbDBMSG = new JLabel("DB 메세지 창입니다.");
		lbDBMSG.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lbDBMSG.setBounds(245, 15, 554, 20);
		frame.getContentPane().add(lbDBMSG);

		JLabel lbShowExcel = new JLabel("*Excel출력");
		lbShowExcel.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lbShowExcel.setForeground(new Color(0, 0, 255));
		lbShowExcel.setHorizontalAlignment(SwingConstants.LEFT);
		lbShowExcel.setBounds(15, 440, 70, 20);
		frame.getContentPane().add(lbShowExcel);

		tfPath = new JTextField();
		tfPath.setText("D:");
		tfPath.setColumns(10);
		tfPath.setBounds(90, 470, 300, 20);
		frame.getContentPane().add(tfPath);

		lbSavedPathMSG = new JLabel("저장 경로 메세지 입니다.");
		lbSavedPathMSG.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lbSavedPathMSG.setBounds(90, 495, 709, 20);
		frame.getContentPane().add(lbSavedPathMSG);

		JLabel lbShowSavedPath = new JLabel("저장경로 :");
		lbShowSavedPath.setForeground(new Color(128, 0, 128));
		lbShowSavedPath.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lbShowSavedPath.setHorizontalAlignment(SwingConstants.RIGHT);
		lbShowSavedPath.setBounds(15, 495, 70, 20);
		frame.getContentPane().add(lbShowSavedPath);

		JLabel lbShowMSG1 = new JLabel("MSG :");
		lbShowMSG1.setForeground(new Color(0, 128, 0));
		lbShowMSG1.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lbShowMSG1.setHorizontalAlignment(SwingConstants.RIGHT);
		lbShowMSG1.setBounds(175, 15, 60, 20);
		frame.getContentPane().add(lbShowMSG1);

		JLabel lbShowURL = new JLabel("URL");
		lbShowURL.setHorizontalAlignment(SwingConstants.RIGHT);
		lbShowURL.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lbShowURL.setBounds(15, 45, 60, 20);
		frame.getContentPane().add(lbShowURL);

		JLabel lbShowIDandPWD = new JLabel("ID/PWD");
		lbShowIDandPWD.setHorizontalAlignment(SwingConstants.RIGHT);
		lbShowIDandPWD.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lbShowIDandPWD.setBounds(15, 70, 60, 20);
		frame.getContentPane().add(lbShowIDandPWD);

		JLabel lbShowCurrentID = new JLabel("현재ID :");
		lbShowCurrentID.setForeground(new Color(128, 0, 128));
		lbShowCurrentID.setHorizontalAlignment(SwingConstants.RIGHT);
		lbShowCurrentID.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lbShowCurrentID.setBounds(505, 70, 60, 20);
		frame.getContentPane().add(lbShowCurrentID);

		lbCurrentID = new JLabel("(not connected)");
		lbCurrentID.setForeground(new Color(255, 0, 0));
		lbCurrentID.setFont(new Font("굴림", Font.PLAIN, 14));
		lbCurrentID.setBounds(575, 70, 200, 20);
		frame.getContentPane().add(lbCurrentID);

		JLabel lbShowMSG2 = new JLabel("MSG :");
		lbShowMSG2.setHorizontalAlignment(SwingConstants.RIGHT);
		lbShowMSG2.setForeground(new Color(0, 128, 0));
		lbShowMSG2.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lbShowMSG2.setBounds(190, 440, 60, 20);
		frame.getContentPane().add(lbShowMSG2);

		JLabel lbShowMSG3 = new JLabel("MSG :");
		lbShowMSG3.setHorizontalAlignment(SwingConstants.RIGHT);
		lbShowMSG3.setForeground(new Color(0, 128, 0));
		lbShowMSG3.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lbShowMSG3.setBounds(375, 110, 60, 20);
		frame.getContentPane().add(lbShowMSG3);

		lbSearchMSG = new JLabel("검색 메세지 입니다.");
		lbSearchMSG.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lbSearchMSG.setBounds(445, 110, 354, 20);
		frame.getContentPane().add(lbSearchMSG);

		lbExcelMSG = new JLabel("엑셀 출력 메세지 입니다.");
		lbExcelMSG.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lbExcelMSG.setBounds(255, 440, 544, 20);
		frame.getContentPane().add(lbExcelMSG);

		btnDefaultPath = new JButton("기본경로");
		btnDefaultPath.setBackground(UIManager.getColor("Button.background"));
		btnDefaultPath.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		btnDefaultPath.setBounds(400, 470, 90, 25);
		frame.getContentPane().add(btnDefaultPath);
		btnDefaultPath.addActionListener(this);

		btnDelete = new JButton("삭제");
		btnDelete.setBackground(UIManager.getColor("Button.background"));
		btnDelete.setFont(new Font("함초롬돋움", Font.BOLD, 13));
		btnDelete.setBounds(15, 195, 60, 60);
		frame.getContentPane().add(btnDelete);
		btnDelete.addActionListener(this);

	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnConnect)
			newConnectAndsetBasicCbbView();
		else if(e.getSource() == cbbView) 
			defaultOrselectedComboboxView();
		else if(e.getSource() == cbbColumn)
			defaultOrselectedComboboxColumn();
		else if(e.getSource() == btnSearch)
			searchCondition();
		else if(e.getSource() == btnExcel)
			createExcelFile();
		else if(e.getSource() == btnDelete)
			deleteListColumn();
		else if(e.getSource() == btnDefaultPath)
			selectDefaultPath();
	}

	public void connectDB() {

		try {
			if(rs 	 != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(con 	 != null) con.close();

			Class.forName(driver);
			lbDBMSG.setForeground(Color.BLUE);
			lbDBMSG.setText("Driver loading complete.");

			url = "jdbc:oracle:thin:@" + tfIP.getText() + ":" + tfPort.getText() + ":" + tfSid.getText();
			con = DriverManager.getConnection(url, tfID.getText(), tfPWD.getText());
			lbDBMSG.setForeground(Color.BLUE);
			lbDBMSG.setText("DB connecting complete.");

			lbCurrentID.setForeground(Color.BLUE);
			lbCurrentID.setText(tfID.getText());

		} catch(ClassNotFoundException e) {

			lbDBMSG.setForeground(Color.RED);
			lbDBMSG.setText("DB driver loading failed.");
			lbCurrentID.setForeground(Color.RED);
			lbCurrentID.setText("(Can't Loading)");

		} catch(SQLException e) {

			lbDBMSG.setForeground(Color.RED);
			lbDBMSG.setText("DB connecting failed.");
			lbCurrentID.setForeground(Color.RED);
			lbCurrentID.setText("(Can't connected)");

		}

	}

	public void newConnectAndsetBasicCbbView() {

		try {
			connectDB();

			sql    = "SELECT table_name FROM tabs";
			pstmt  = con.prepareStatement(sql);
			rs 	   = pstmt.executeQuery();

			cbbView.removeAllItems();
			cbbView.setForeground(Color.LIGHT_GRAY);
			cbbView.addItem(defaultViewString); 
			/*	
			 * >>#주의 : Combobox에 Item 추가시 cbbView에 의해 actionPerformed()메소드가 호출됨.<<
			 * >>현재 ResultSet에 SQL을 실행한 결과가 테이블 형태로 저장되어 있다. 이순간에 cbbView가 호출되면
			 *   SQL을 2차적으로 중복해서 실행하므로 오류가 발생한다.<<
			 * >>따라서 지금은 cbbView가 실행되지 못하도록 동기화 코드가 필요하다.<<
			 */

			while(rs.next())
				cbbView.addItem(rs.getString(1));

		}catch(SQLException e){

			lbDBMSG.setForeground(Color.RED);
			lbDBMSG.setText("SQL executing failed.");

		}finally{

			try{ if(rs!=null) 	 rs.close();
			}catch(SQLException e1){};
			try{ if(pstmt!=null) pstmt.close();
			}catch(SQLException e1){};

		}
	}

	private void defaultOrselectedComboboxView() {
		//>>#동기화 코드 : cbbView.getSelectedItem()!=null (참고로 이것때문에 고생)<<
		if(cbbView.getSelectedItem() != null && !defaultViewString.equals( cbbView.getSelectedItem() ))
			descColumnsinView();
		else{
			selectedView = null;
			cbbView.setForeground(Color.LIGHT_GRAY);
			cbbColumn.removeAllItems();
			cbbColumn.setForeground(Color.LIGHT_GRAY);
			cbbColumn.addItem("(Not Found)");
		}
	}

	private void defaultOrselectedComboboxColumn() {
		if("(Not Found)".equals( cbbColumn.getSelectedItem() )){

			selectedColumns.clear();
			dlModelColumn.clear();

		}else if(cbbColumn.getSelectedItem() == null || "*".equals( cbbColumn.getSelectedItem() )){

			cbbColumn.setForeground(Color.LIGHT_GRAY);
			selectedColumns.clear();
			dlModelColumn.clear();

		}else if(selectedView != null && cbbView.getSelectedItem() != null)
			selectColumnsinView();
	}

	public void descColumnsinView(){

		try {
			cbbView.setForeground(Color.BLACK);

			sql 	= "SELECT * FROM "+cbbView.getSelectedItem();
			pstmt   = con.prepareStatement(sql);
			rs		= pstmt.executeQuery();

			ResultSetMetaData metaData = rs.getMetaData();

			allColumnsinView.clear();
			cbbColumn.removeAllItems();
			selectedColumns.clear();
			selectedColumnDataTypes.clear();
			dlModelColumn.clear();

			cbbColumn.setForeground( Color.LIGHT_GRAY );
			cbbColumn.addItem( "*" );
			selectedView = (String)cbbView.getSelectedItem();

			String str = null;
			for(int i = 1 ; i <= metaData.getColumnCount() ; i++ ){
				str = metaData.getColumnName(i);
				selectedColumnDataTypes.add( metaData.getColumnTypeName(i) );
				cbbColumn.addItem( str );
				allColumnsinView.add( str );
			}

		}catch(SQLException e){

			lbDBMSG.setForeground(Color.RED);
			lbDBMSG.setText("DB SQL desc selectedColumns executing failed.");

		}finally{

			try{ if(rs!=null) rs.close();
			}catch(SQLException e1){};
			try{ if(pstmt!=null) pstmt.close();
			}catch(SQLException e1){};

		}
	}

	public void selectColumnsinView(){

		try{
			cbbColumn.setForeground(Color.BLACK);

			if(!selectedColumns.isEmpty()) {
				for(int i = 0 ; i < selectedColumns.size() ; i++) {
					if(cbbColumn.getSelectedItem().equals(selectedColumns.get(i)))
						throw new BizException();
				}
			}

			dlModelColumn.addElement( (String) cbbColumn.getSelectedItem() );
			selectedColumns.add( (String) cbbColumn.getSelectedItem() );
			selectedColumnDataTypes.add( selectedColumnDataTypes.get(cbbColumn.getSelectedIndex()-1) );
			//>>#selectedColumnDataTypes.size() : 전체 데이터타입(MaxColumn개) + 선택한 데이터 타입<<

		}catch(BizException e){}
	}

	public void searchCondition() {

		try{
			if(selectedView==null)
				throw new BizException();

			excelHeader.clear();
			excelData.clear();

			StringBuffer bufferForMergeColumns = new StringBuffer();

			if( selectedColumns.isEmpty() ){

				bufferForMergeColumns.append( "*" );

				for(int i=0 ; i < allColumnsinView.size() ; i++)
					excelHeader.add( allColumnsinView.get(i) );

			} else {

				for(int i=0 ; i < selectedColumns.size() ; i++) {

					bufferForMergeColumns.append(selectedColumns.get(i));
					if( (i+1) != selectedColumns.size() )
						bufferForMergeColumns.append( "," );

					excelHeader.add( selectedColumns.get(i) );
				}
			}

			if( selectedColumns.isEmpty() ) {

				columnsCount = allColumnsinView.size();
				dtModel 	 = new DefaultTableModel( allColumnsinView, 0 );

			} else {

				columnsCount = selectedColumns.size();
				dtModel 	 = new DefaultTableModel( selectedColumns, 0 );

			}

			table = new JTable();
			/*
			 * >>#의문 : scrollPane.setColumnHeaderView(table); 이게 기본 설정되어있던데
			 *  이건 뭐하는 거지? table대신 null을 넣어도 된다는데 뭔지 모르겠음.<<
			 */

			table.setAutoCreateColumnsFromModel(false);
			table.setModel(dtModel);

			for(int i=0 ; i < columnsCount ; i++) {

				DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
				renderer.setHorizontalAlignment(JLabel.CENTER);
				TableColumn column = new TableColumn( i, 50, renderer, null);
				table.addColumn(column);

			}

			table.setFocusable(false); //>>#의문 : 뭔지 까먹었다.<<
			scrollPane.setViewportView(table);

			sql 	= "SELECT "+bufferForMergeColumns.toString()+" FROM "+selectedView;
			pstmt 	= con.prepareStatement(sql);
			rs 		= pstmt.executeQuery();

			String[] str = new String[columnsCount]; //>>#보완필요 : 매번 조회시 자원낭비하기 때문에 개선방안 마련필요.<<
			rowsCount = 0;
			while(rs.next()) {

				for(int i = 0 ; i < columnsCount ; i++) {

					if( selectedColumns.isEmpty() ) {

						if( "NUMBER".equals( selectedColumnDataTypes.get(i) ) )							
							str[i] = String.valueOf( rs.getInt(i+1) );
						else
							str[i] = rs.getString(i+1);
						excelData.add(str[i]);

					} else {

						if("NUMBER".equals( selectedColumnDataTypes.get(allColumnsinView.size() + i) ))
							str[i] = String.valueOf( rs.getInt(i+1) );
						else
							str[i] = rs.getString(i+1);
						excelData.add(str[i]);

					}

				}
				dtModel.addRow(str);
				rowsCount++;
			}
			if( rowsCount == 0 ) {

				lbSearchMSG.setForeground(Color.RED);
				lbSearchMSG.setText("자료가 없습니다.");

			} else {

				lbSearchMSG.setForeground(Color.BLUE);
				lbSearchMSG.setText(selectedView+" 테이블 조회 완료.");

			}

		}catch(BizException e){

			lbSearchMSG.setForeground(Color.RED);
			lbSearchMSG.setText("조건을 선택하세요.");

		}catch(SQLException e){

			lbDBMSG.setForeground(Color.RED);
			lbDBMSG.setText("DB SQL desc selectedColumns executing failed.");

		}finally{

			try{ if(rs!=null) rs.close();
			}catch(SQLException e1){};
			try{ if(pstmt!=null) pstmt.close();
			}catch(SQLException e1){};

		}
	}

	public void deleteListColumn() {

		if(listColumn.getSelectedIndex() == -1) {}
		else {

			selectedColumns.removeElement ( listColumn.getSelectedValue() );
			selectedColumnDataTypes.remove( allColumnsinView.size() + listColumn.getSelectedIndex());
			dlModelColumn.remove		  ( listColumn.getSelectedIndex() );

		}
	}

	@SuppressWarnings("deprecation")

	public void createExcelFile() {

		XSSFWorkbook newExcelFile = new XSSFWorkbook();
		XSSFSheet 	 newSheet 	  = newExcelFile.createSheet(selectedView);
		XSSFRow 	 newRow 	  = newSheet.createRow(0);
		XSSFCell 	 newCell 	  = null;

		XSSFFont 	 fontTitle 	  = newExcelFile.createFont();
		fontTitle.setFontHeightInPoints( (short)18 );
		fontTitle.setFontName( "HY헤드라인M" );
		fontTitle.setBold( true );
		fontTitle.setColor( HSSFColor.BLUE.index );

		XSSFCellStyle csTitle 	  = newExcelFile.createCellStyle();
		csTitle.setFont( fontTitle );
		csTitle.setAlignment( XSSFCellStyle.ALIGN_CENTER );	//>>#tips : 가운데 줄 표시는 앞으로 사라지게될 기능이라는 의미<<
		csTitle.setBorderTop( XSSFCellStyle.BORDER_MEDIUM );
		csTitle.setBorderBottom( XSSFCellStyle.BORDER_DOUBLE );
		csTitle.setFillForegroundColor( HSSFColor.LIGHT_YELLOW.index );	// 배경색 설정
		csTitle.setFillPattern( HSSFCellStyle.SOLID_FOREGROUND );		// 배경색 적용

		int[] bufferForDecideColumnWidth = new int[columnsCount];

		for(int i = 0 ; i < columnsCount ; i++){

			if( i == 0 ) 				csTitle.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
			else 		 				csTitle.setBorderLeft(XSSFCellStyle.BORDER_THIN);

			if( (i+1) == columnsCount ) csTitle.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
			else						csTitle.setBorderLeft(XSSFCellStyle.BORDER_THIN);

			newCell = newRow.createCell(i);
			newCell.setCellValue( excelHeader.get(i) );
			newCell.setCellStyle( csTitle );
			newRow.setHeight( (short)700 );
			bufferForDecideColumnWidth[i] = excelHeader.get(i).length();

		}

		XSSFFont 	  fontData 	= newExcelFile.createFont();
		fontData.setFontHeightInPoints( (short)14 );
		fontData.setFontName( "HY헤드라인M" );
		fontData.setBold( false );

		XSSFCellStyle csData 	= newExcelFile.createCellStyle();
		csData.setFont( fontData );
		csData.setAlignment( XSSFCellStyle.ALIGN_CENTER );

		for(int i = 0 ; i < rowsCount ; i++) {

			newRow = newSheet.createRow( i+1 );
			newRow.setHeight( (short)500 );

			for(int j = 0 ; j < columnsCount ; j++) {

				newCell = newRow.createCell( j );
				newCell.setCellValue( excelData.get(i*columnsCount+j) );

				if( j == 0 )				csData.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
				else						csData.setBorderLeft(XSSFCellStyle.BORDER_THIN);

				if( (j+1) == columnsCount )	csData.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
				else						csData.setBorderLeft(XSSFCellStyle.BORDER_THIN);

				if( (i+1) == rowsCount )	csData.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
				else						csData.setBorderBottom(XSSFCellStyle.BORDER_THIN);

				if( excelData.get( i*columnsCount+j ) != null && 
						bufferForDecideColumnWidth[j] < excelData.get( i*columnsCount+j ).length()
						)	bufferForDecideColumnWidth[j] = excelData.get( i*columnsCount+j ).length();

				if( (i+1) == rowsCount ) 	newSheet.setColumnWidth(j, bufferForDecideColumnWidth[j]*500);

				newCell.setCellStyle(csData);

			}

		}

		StringBuffer bufferForMergeAndDescribeColumns = new StringBuffer();
		if( selectedColumns.isEmpty() ) {

			for(int i = 0 ; i < allColumnsinView.size() ;i++) {

				bufferForMergeAndDescribeColumns.append( allColumnsinView.get(i) );
				if( (i+1) != allColumnsinView.size() )
					bufferForMergeAndDescribeColumns.append( "," );

			}

		} else {
			for(int i = 0 ; i < selectedColumns.size() ; i++) {

				bufferForMergeAndDescribeColumns.append( selectedColumns.get(i) );
				if( (i+1) != selectedColumns.size() )
					bufferForMergeAndDescribeColumns.append( "," );

			}

		}

		File file = new File(tfPath.getText()+"\\"+selectedView+"("+bufferForMergeAndDescribeColumns.toString()+")"+".xlsx");
		FileOutputStream fileOut = null;
		try{

			fileOut = new FileOutputStream(file);
			newExcelFile.write(fileOut);
			lbExcelMSG.setForeground(Color.BLUE);
			lbExcelMSG.setText(tfPath.getText()+"\\"+selectedView+"("+bufferForMergeAndDescribeColumns.toString()+")"+".xlsx 파일을 생성하였습니다.");
			lbSavedPathMSG.setForeground(Color.BLUE);

		}catch(FileNotFoundException e) {

			lbExcelMSG.setForeground(Color.RED);
			lbExcelMSG.setText("경로가 잘못 되어 파일을 생성할 수 없습니다.");
			lbSavedPathMSG.setForeground(Color.RED);

		}catch(IOException e){

			lbExcelMSG.setForeground(Color.RED);
			lbExcelMSG.setText("출력할 수 없습니다.");
			lbSavedPathMSG.setForeground(Color.RED);

		}catch(Exception e){

			lbExcelMSG.setForeground(Color.RED);
			lbExcelMSG.setText("오류가 발생하였습니다.");
			lbSavedPathMSG.setForeground(Color.RED);

		}finally{

			try{if(fileOut!=null) fileOut.close();
			}catch(IOException e1){}
			try{if(newExcelFile!=null) newExcelFile.close();
			}catch(IOException e1){}
			lbSavedPathMSG.setText(tfPath.getText()+"\\"+selectedView+"("+bufferForMergeAndDescribeColumns.toString()+")"+".xlsx");

		}
	}

	public void selectDefaultPath() {
		tfPath.setText("D:");
		lbSavedPathMSG.setForeground(Color.BLACK);
		lbSavedPathMSG.setText("D:");
	}

	//	public void debug(){
	//		if(con!=null)
	//			System.out.println("## CON : "+con.toString());
	//		if(pstmt!=null)
	//			System.out.println("## PSTMT : "+pstmt.toString());
	//		if(rs!=null)
	//			System.out.println("## RS : "+rs.toString());
	//		System.out.println("01. selectedView : "+selectedView);
	//		System.out.println("02. selectedColumns.size() : "+selectedColumns.size());
	//		for(int i = 0 ; i < selectedColumns.size() ; i++)
	//			System.out.print("["+selectedColumns.get(i)+"]");
	//		System.out.println("03. selectedColumnDataTypes.size() : "+selectedColumnDataTypes.size());
	//		for(int i = 0 ; i < selectedColumnDataTypes.size() ; i++)
	//			System.out.print("["+selectedColumnDataTypes.get(i)+"]");
	//		System.out.println("04. allColumnsinView.size() : "+allColumnsinView.size());
	//		for(int i = 0 ; i < allColumnsinView.size() ; i++)
	//			System.out.print("["+allColumnsinView.get(i)+"]");
	//		System.out.println("05. excelHeader.size() : "+excelHeader.size());
	//		for(int i = 0 ; i < excelHeader.size() ; i++)
	//			System.out.print("["+excelHeader.get(i)+"]");
	//		System.out.println("06. columnsCount : "+columnsCount);
	//		System.out.println("07. rowsCount : "+rowsCount);
	//		System.out.println("08. excelData.size() : "+excelData.size());
	//		for(int i = 0 ; i < rowsCount ; i++){
	//			for(int j = 0 ; j < columnsCount  ; j++) {
	//				System.out.print("["+excelData.get(i*columnsCount+j)+"]");
	//			}
	//			System.out.println();
	//		}
	//		System.out.println("09. driver : "+driver);
	//		System.out.print("10. con is null : "+(con==null));
	//		if(con!=null){
	//			try {
	//				System.out.println(" , con.isClosed() : "+con.isClosed());
	//			} catch (SQLException e) {
	//				e.printStackTrace();
	//				System.out.println(">>> con closed 오류발생 <<<");
	//			}
	//		}
	//		else
	//			System.out.println();
	//		System.out.print("11. pstmt is null : "+(pstmt==null));
	//		if(pstmt!=null){
	//			try {
	//				System.out.println(" , pstmt.isClosed() : "+pstmt.isClosed());
	//			} catch (SQLException e) {
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//				System.out.println(">>> pstmt closed 오류발생 <<<");
	//			}
	//		}
	//		else
	//			System.out.println();
	//		System.out.print("12. rs is null : "+(rs==null));
	//		if(rs!=null){
	//			try {
	//				System.out.println(" , rs.isClosed() : "+rs.isClosed());
	//			} catch (SQLException e) {
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//				System.out.println(">>> rs closed 오류발생 <<<");
	//			}
	//		}
	//		else
	//			System.out.println();
	//	}

}

class BizException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BizException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BizException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}