package TeamProject.GiveDBFile;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;

public class GiveDBFileMain {

	private JFrame frame;
	private JTable table;
	private JTextField tfIP;
	private JTextField tfPort;
	private JTextField tfSid;
	private JTextField tfID;
	private JTextField tfPWD;
	private JTextField tfPath;
	private JButton btnExcel;

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

		JButton btnSearch = new JButton("조회");
		btnSearch.setBounds(280, 110, 90, 25);
		frame.getContentPane().add(btnSearch);

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

		JComboBox cbbView = new JComboBox();
		cbbView.setBounds(80, 140, 110, 20);
		frame.getContentPane().add(cbbView);
		
		JLabel lblNewLabel_1 = new JLabel("Column :");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lblNewLabel_1.setBounds(15, 165, 60, 20);
		frame.getContentPane().add(lblNewLabel_1);
		
		JComboBox cbbColumn = new JComboBox();
		cbbColumn.setBounds(80, 165, 110, 20);
		frame.getContentPane().add(cbbColumn);
		
		JScrollPane spColumn = new JScrollPane();
		spColumn.setBounds(80, 195, 110, 225);
		frame.getContentPane().add(spColumn);
		
		btnExcel = new JButton("출력");
		btnExcel.setBounds(90, 440, 90, 25);
		frame.getContentPane().add(btnExcel);
		
		JButton btnReflash = new JButton("초기화");
		btnReflash.setBounds(100, 110, 90, 25);
		frame.getContentPane().add(btnReflash);
		
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
		tfIP.setBounds(155, 45, 120, 20);
		frame.getContentPane().add(tfIP);
		tfIP.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("IP :");
		lblNewLabel_5.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_5.setBounds(85, 45, 60, 20);
		frame.getContentPane().add(lblNewLabel_5);
		
		tfPort = new JTextField();
		tfPort.setColumns(10);
		tfPort.setBounds(365, 45, 120, 20);
		frame.getContentPane().add(tfPort);
		
		JLabel lblPort = new JLabel("PORT :");
		lblPort.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPort.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lblPort.setBounds(295, 45, 60, 20);
		frame.getContentPane().add(lblPort);
		
		tfSid = new JTextField();
		tfSid.setColumns(10);
		tfSid.setBounds(575, 45, 120, 20);
		frame.getContentPane().add(tfSid);
		
		JLabel lblSid = new JLabel("SID :");
		lblSid.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lblSid.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSid.setBounds(505, 45, 60, 20);
		frame.getContentPane().add(lblSid);
		
		JButton btnConnect = new JButton("연결");
		btnConnect.setBounds(80, 15, 90, 25);
		frame.getContentPane().add(btnConnect);
		
		JLabel lblNewLabel_6 = new JLabel("ID :");
		lblNewLabel_6.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_6.setBounds(85, 70, 60, 20);
		frame.getContentPane().add(lblNewLabel_6);
		
		tfID = new JTextField();
		tfID.setBounds(155, 70, 120, 20);
		frame.getContentPane().add(tfID);
		tfID.setColumns(10);
		
		JLabel lblPwd = new JLabel("PWD :");
		lblPwd.setFont(new Font("함초롬돋움", Font.BOLD, 14));
		lblPwd.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPwd.setBounds(295, 70, 60, 20);
		frame.getContentPane().add(lblPwd);
		
		tfPWD = new JTextField();
		tfPWD.setColumns(10);
		tfPWD.setBounds(365, 70, 120, 20);
		frame.getContentPane().add(tfPWD);
		
		JLabel lbDBMSG = new JLabel("New label");
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
		
		JLabel label_6 = new JLabel("New label");
		label_6.setFont(new Font("굴림", Font.PLAIN, 14));
		label_6.setBounds(575, 70, 200, 20);
		frame.getContentPane().add(label_6);
		
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
		
		JButton btnPathSave = new JButton("저장");
		btnPathSave.setBounds(400, 470, 90, 25);
		frame.getContentPane().add(btnPathSave);
		
		JButton btnDefaultPath = new JButton("기본경로");
		btnDefaultPath.setBounds(500, 470, 90, 25);
		frame.getContentPane().add(btnDefaultPath);

		
	}
}