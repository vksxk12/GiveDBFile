package TeamProject.GiveDBFile;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

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

public class GiveDBFileMain implements ActionListener {

	private JFrame frame;
	private JTable table;
	private JTextField tfIP;
	private JTextField tfPort;
	private JTextField tfSid;
	private JTextField tfID;
	private JTextField tfPWD;
	private JTextField tfPath;

	private JButton btnExcel;
	private JButton btnConnect;
	private JButton btnReset;
	private JButton btnSearch;
	private JButton btnPathSave;
	private JButton btnDefaultPath;
	private JButton btnDelete;

	private JLabel lbDBMSG;
	private JLabel lbCurrentID;
	private JList<String> list;

	private JComboBox<String> cbbView;
	private JComboBox<String> cbbColumn;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url;
	private String view;
	private ArrayList<String> columns = new ArrayList<String>();
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String sql;
	//	private DefaultListModel<String> Columnlist = new DefaultListModel<String>();
	private DefaultListModel<String> Columnlist = new DefaultListModel<String>();

	private String defvstr = "(Select View)";//Default View String

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
		frame.setBounds(100, 100, 841, 564);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(220, 140, 579, 280);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setColumnHeaderView(table);

		JLabel lblNewLabel = new JLabel("*검색결과");
		lblNewLabel.setForeground(new Color(0, 0, 255));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lblNewLabel.setBounds(210, 110, 65, 20);
		frame.getContentPane().add(lblNewLabel);

		btnSearch = new JButton("조회");
		btnSearch.setBounds(280, 110, 90, 25);
		frame.getContentPane().add(btnSearch);
		btnSearch.addActionListener(this);

		JLabel label = new JLabel("*검색조건");
		label.setForeground(new Color(0, 0, 255));
		label.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setBounds(15, 110, 65, 20);
		frame.getContentPane().add(label);

		JLabel lblView = new JLabel("View :");
		lblView.setHorizontalAlignment(SwingConstants.RIGHT);
		lblView.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lblView.setBounds(15, 140, 60, 20);
		frame.getContentPane().add(lblView);

		cbbView = new JComboBox<String>();
		cbbView.setBounds(80, 140, 110, 20);
		frame.getContentPane().add(cbbView);
		cbbView.addActionListener(this);

		JLabel lblNewLabel_1 = new JLabel("Column :");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lblNewLabel_1.setBounds(15, 165, 60, 20);
		frame.getContentPane().add(lblNewLabel_1);

		cbbColumn = new JComboBox<String>();
		cbbColumn.setBounds(80, 165, 110, 20);
		frame.getContentPane().add(cbbColumn);
		cbbColumn.addActionListener(this);

		JScrollPane spColumn = new JScrollPane();
		spColumn.setBounds(80, 195, 110, 225);
		frame.getContentPane().add(spColumn);

		list = new JList<String>(Columnlist);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setVisibleRowCount(20);
		list.setBackground(new Color(255, 255, 255));
		spColumn.setColumnHeaderView(list);

		btnExcel = new JButton("출력");
		btnExcel.setBounds(90, 440, 90, 25);
		frame.getContentPane().add(btnExcel);
		btnExcel.addActionListener(this);

		btnReset = new JButton("초기화");
		btnReset.setBounds(100, 110, 90, 25);
		frame.getContentPane().add(btnReset);
		btnReset.addActionListener(this);

		JLabel lblNewLabel_2 = new JLabel("경로지정 :");
		lblNewLabel_2.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setBounds(15, 470, 70, 20);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("*DB연결");
		lblNewLabel_3.setForeground(new Color(0, 0, 255));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_3.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lblNewLabel_3.setBounds(15, 15, 60, 20);
		frame.getContentPane().add(lblNewLabel_3);

		tfIP = new JTextField();
		tfIP.setText("localhost");
		tfIP.setBounds(155, 45, 120, 20);
		frame.getContentPane().add(tfIP);
		tfIP.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("IP :");
		lblNewLabel_5.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_5.setBounds(85, 45, 60, 20);
		frame.getContentPane().add(lblNewLabel_5);

		tfPort = new JTextField();
		tfPort.setText("1521");
		tfPort.setColumns(10);
		tfPort.setBounds(365, 45, 120, 20);
		frame.getContentPane().add(tfPort);

		JLabel lblPort = new JLabel("PORT :");
		lblPort.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPort.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lblPort.setBounds(295, 45, 60, 20);
		frame.getContentPane().add(lblPort);

		tfSid = new JTextField();
		tfSid.setText("XE");
		tfSid.setColumns(10);
		tfSid.setBounds(575, 45, 120, 20);
		frame.getContentPane().add(tfSid);

		JLabel lblSid = new JLabel("SID :");
		lblSid.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lblSid.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSid.setBounds(505, 45, 60, 20);
		frame.getContentPane().add(lblSid);

		btnConnect = new JButton("연결");
		btnConnect.setBounds(80, 15, 90, 25);
		frame.getContentPane().add(btnConnect);
		btnConnect.addActionListener(this);

		JLabel lblNewLabel_6 = new JLabel("ID :");
		lblNewLabel_6.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_6.setBounds(85, 70, 60, 20);
		frame.getContentPane().add(lblNewLabel_6);

		tfID = new JTextField();
		tfID.setText("madang");
		tfID.setBounds(155, 70, 120, 20);
		frame.getContentPane().add(tfID);
		tfID.setColumns(10);

		JLabel lblPwd = new JLabel("PWD :");
		lblPwd.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lblPwd.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPwd.setBounds(295, 70, 60, 20);
		frame.getContentPane().add(lblPwd);

		tfPWD = new JTextField();
		tfPWD.setText("madang");
		tfPWD.setColumns(10);
		tfPWD.setBounds(365, 70, 120, 20);
		frame.getContentPane().add(tfPWD);

		lbDBMSG = new JLabel("New label");
		lbDBMSG.setFont(new Font("굴림", Font.PLAIN, 14));
		lbDBMSG.setBounds(245, 15, 300, 20);
		frame.getContentPane().add(lbDBMSG);

		JLabel lblexcel = new JLabel("*Excel출력");
		lblexcel.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lblexcel.setForeground(new Color(0, 0, 255));
		lblexcel.setHorizontalAlignment(SwingConstants.LEFT);
		lblexcel.setBounds(15, 440, 70, 20);
		frame.getContentPane().add(lblexcel);

		tfPath = new JTextField();
		tfPath.setText("C://DB.xls");
		tfPath.setColumns(10);
		tfPath.setBounds(90, 470, 300, 20);
		frame.getContentPane().add(tfPath);

		JLabel lbCurrentPath = new JLabel("New label");
		lbCurrentPath.setBounds(90, 495, 300, 20);
		frame.getContentPane().add(lbCurrentPath);

		JLabel label_3 = new JLabel("현재경로 :");
		label_3.setForeground(new Color(128, 0, 128));
		label_3.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setBounds(15, 495, 70, 20);
		frame.getContentPane().add(label_3);

		JLabel lblmsg = new JLabel("MSG :");
		lblmsg.setForeground(new Color(0, 128, 0));
		lblmsg.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lblmsg.setHorizontalAlignment(SwingConstants.RIGHT);
		lblmsg.setBounds(175, 15, 60, 20);
		frame.getContentPane().add(lblmsg);

		JLabel lblUrl = new JLabel("URL");
		lblUrl.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUrl.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lblUrl.setBounds(15, 45, 60, 20);
		frame.getContentPane().add(lblUrl);

		JLabel lblIdpwd = new JLabel("ID/PWD");
		lblIdpwd.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIdpwd.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lblIdpwd.setBounds(15, 70, 60, 20);
		frame.getContentPane().add(lblIdpwd);

		JLabel lblid = new JLabel("현재ID :");
		lblid.setForeground(new Color(128, 0, 128));
		lblid.setHorizontalAlignment(SwingConstants.RIGHT);
		lblid.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lblid.setBounds(505, 70, 60, 20);
		frame.getContentPane().add(lblid);

		lbCurrentID = new JLabel("(not connected)");
		lbCurrentID.setForeground(new Color(255, 0, 0));
		lbCurrentID.setFont(new Font("굴림", Font.PLAIN, 14));
		lbCurrentID.setBounds(575, 70, 200, 20);
		frame.getContentPane().add(lbCurrentID);

		JLabel label_4 = new JLabel("MSG :");
		label_4.setHorizontalAlignment(SwingConstants.RIGHT);
		label_4.setForeground(new Color(0, 128, 0));
		label_4.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		label_4.setBounds(190, 440, 60, 20);
		frame.getContentPane().add(label_4);

		JLabel label_5 = new JLabel("MSG :");
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setForeground(new Color(0, 128, 0));
		label_5.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		label_5.setBounds(375, 110, 60, 20);
		frame.getContentPane().add(label_5);

		JLabel lbSearchMSG = new JLabel("New label");
		lbSearchMSG.setFont(new Font("굴림", Font.PLAIN, 14));
		lbSearchMSG.setBounds(445, 110, 300, 20);
		frame.getContentPane().add(lbSearchMSG);

		JLabel lbExcelMSG = new JLabel("New label");
		lbExcelMSG.setFont(new Font("굴림", Font.PLAIN, 14));
		lbExcelMSG.setBounds(255, 440, 200, 20);
		frame.getContentPane().add(lbExcelMSG);

		btnPathSave = new JButton("저장");
		btnPathSave.setBounds(400, 470, 90, 25);
		frame.getContentPane().add(btnPathSave);
		btnPathSave.addActionListener(this);

		btnDefaultPath = new JButton("기본경로");
		btnDefaultPath.setBounds(500, 470, 90, 25);
		frame.getContentPane().add(btnDefaultPath);

		btnDelete = new JButton("삭제");
		btnDelete.setBounds(15, 195, 60, 60);
		frame.getContentPane().add(btnDelete);
		btnDelete.addActionListener(this);

	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnConnect) {
			connect();
		}else if(e.getSource() == btnSearch) {
			//			System.out.println("조회버튼입니다.");
			search();
		}else if(e.getSource() == btnReset) {
			System.out.println("초기화버튼입니다.");
		}else if(e.getSource() == btnExcel) {
			System.out.println("엑셀출력버튼입니다.");
		}else if(e.getSource() == btnPathSave) {
			System.out.println("경로저장버튼입니다.");
		}else if(e.getSource() == btnDefaultPath) {
			System.out.println("기본경로버튼입니다.");
		}else if(e.getSource() == btnDelete) {
			//			System.out.println("삭제버튼입니다.");
			delete();
		}else if(e.getSource() == cbbView) {
			if(!defvstr.equals(cbbView.getSelectedItem()))
				descColumnsinView();
			else{
				cbbView.setForeground(Color.LIGHT_GRAY);
				cbbColumn.removeAllItems();
				cbbColumn.setForeground(Color.LIGHT_GRAY);
				cbbColumn.addItem("(Not Found)");
			}
		}else if(e.getSource() == cbbColumn) {
			if("(Not Found)".equals(cbbColumn.getSelectedItem())){
				cbbColumn.removeAllItems();
				cbbColumn.setForeground(Color.LIGHT_GRAY);
				cbbColumn.addItem("(Not Found)");
				columns.clear();
				Columnlist.clear();
			}else if("*".equals(cbbColumn.getSelectedItem())||cbbColumn.getSelectedItem()==null){
				cbbColumn.setForeground(Color.LIGHT_GRAY);
			}else
				selectColumnsinView();
		}
	}

	public void dbconnect() {
		try {
			if(rs!=null)
				rs.close();
			if(pstmt!=null)
				pstmt.close();
			if(con!=null)
				con.close();
			Class.forName(driver);
			lbDBMSG.setForeground(Color.BLUE);
			lbDBMSG.setText("driver loading complete.");
			url = "jdbc:oracle:thin:@"+tfIP.getText()+":"+tfPort.getText()+":"+tfSid.getText();
			con = DriverManager.getConnection(url,tfID.getText(),tfPWD.getText());
			lbDBMSG.setForeground(Color.BLUE);
			lbDBMSG.setText("DB connecting complete.");
			lbCurrentID.setForeground(Color.BLUE);
			lbCurrentID.setText(tfID.getText());	
		}catch(ClassNotFoundException e){
			lbDBMSG.setForeground(Color.RED);
			lbDBMSG.setText("DB driver loading failed.");
			lbCurrentID.setForeground(Color.RED);
			lbCurrentID.setText("(Not connected)");
		}catch(SQLException e){
			lbDBMSG.setForeground(Color.RED);
			lbDBMSG.setText("DB connecting failed.");
			lbCurrentID.setForeground(Color.RED);
			lbCurrentID.setText("(Not connected)");
			//			try { 
			//				if(con!=null)con.close();
			//			}catch(SQLException e1){};
		}
	}
	public void connect() {
		try {
			dbconnect();
			sql ="SELECT table_name FROM tabs";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			cbbView.removeAllItems();
			cbbView.setForeground(Color.LIGHT_GRAY);
			cbbView.addItem(defvstr);
			while(rs.next()){
				cbbView.addItem(rs.getString(1));
			}
		}catch(SQLException e){
			lbDBMSG.setForeground(Color.RED);
			lbDBMSG.setText("DB SQL executing failed.");
			e.printStackTrace();
			lbCurrentID.setForeground(Color.RED);
			lbCurrentID.setText("(Not connected)");
		}finally{
			try{if(rs!=null)rs.close();
			}catch(SQLException e1){};
			try{if(pstmt!=null)pstmt.close();
			}catch(SQLException e1){};
		}
	}

	public void descColumnsinView(){
		try {
			cbbView.setForeground(Color.BLACK);
			sql = "SELECT * FROM "+cbbView.getSelectedItem();
			pstmt = con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			cbbColumn.removeAllItems();
			columns.clear();
			Columnlist.clear();
			cbbColumn.setForeground(Color.LIGHT_GRAY);
			cbbColumn.addItem("*");
			view = (String)cbbView.getSelectedItem();
			columns.clear();
			String str = null;
			for(int i=1 ; i <= metaData.getColumnCount() ;i++ ){
				str = metaData.getColumnName(i);
				cbbColumn.addItem(str);
			}
		}catch(SQLException e){
			lbDBMSG.setForeground(Color.RED);
			lbDBMSG.setText("DB SQL executing failed.");
			lbCurrentID.setForeground(Color.RED);
			lbCurrentID.setText("(Not connected)");
		}finally{
			try{if(rs!=null)rs.close();
			}catch(SQLException e1){};
			try{if(pstmt!=null)pstmt.close();
			}catch(SQLException e1){};
		}
	}
	public void selectColumnsinView(){
		try{
			cbbColumn.setForeground(Color.BLACK);

			if(!columns.isEmpty()){
				for(int i = 0; i < columns.size(); i++) {
					if(cbbColumn.getSelectedItem().equals(columns.get(i)))
						throw new BizException();
				}
			}
			columns.add((String) cbbColumn.getSelectedItem());
			Columnlist.addElement((String) cbbColumn.getSelectedItem());
		}catch(BizException e){}
	}
	public void search() {
		try{
			//			pstmt = 
		}catch(Exception e){

		}
	}
	public void delete() {
		System.out.println(list.getSelectedValue()+" , "+list.getSelectedIndex());
		if(list.getSelectedIndex()!=-1) {
			Columnlist.remove(list.getSelectedIndex());
			System.out.println("Columnlist 삭제완료 ");
			columns.remove(list.getSelectedIndex()+2);
			System.out.println("columns 삭제완료 ");
			for(int i= 0 ; i<columns.size() ; i++)
				System.out.println(columns.get(i));
		}else{
			System.out.println("삭제불가");
		}
	}

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