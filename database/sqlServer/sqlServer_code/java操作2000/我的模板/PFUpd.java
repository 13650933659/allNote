//����ļ��Ǹ����û�ѡ���Ǹ�һ�м�¼����ʾ�˼�¼����������
package com.mhl.view2;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSetMetaData;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mhl.model.PFModel;
import com.mhl.tools.MyTools;

public class PFUpd extends JDialog implements ActionListener{
	//��������Ի�����������
	JPanel jp1,jp2,jp3;
	JLabel jp1_jl1,jp1_jl2,jp1_jl3,jp1_jl4,jp1_jl5,jp1_jl6,jp1_jl7,jp1_jl8,jp1_jl9,jp1_jl10;
	//JLabel jp1_jl;
	JTextField jp2_jtf1,jp2_jtf2,jp2_jtf3,jp2_jtf4,jp2_jtf5,jp2_jtf6,jp2_jtf7,jp2_jtf8,jp2_jtf9,jp2_jtf10;
	//JTextField jp2_jtf;
	JButton jp3_jb1,jp3_jb2;
	
	//����ڴ�й¶��
	PFModel pfm=null;
	
	//������
//	JLabel jp1_jl=null;
	//ResultSetMetaData rsmd=null;
	//���ݱ�ģ��
	//PFModel pfm=null;
	
	//�����ʼ��
	public PFUpd(Frame owner,String title,boolean modal,PFModel pfm){
		super(owner,title,modal);
		//����������ɱ߽粼��
		/*��������ʰ��*/
//		//���ߵ�
//		jp1=new JPanel(new GridLayout(10,1));
//		for(int i=0;i<pfm.getColumnCount();i++){
//			//����jp1�µ�ʮ��jl��������jp1���Ժ�������jp1_jl������������
//			jp1_jl=new JLabel(pfm.getColumnName(i));
//			jp1.add(jp1_jl);
//		}
//		this.add(jp1,"West");
//		//���ߵ�
//		jp2=new JPanel(new GridLayout(10,1));
//		for(int i=0;i<pfm.getColumnCount();i++){
//			//����jp2�µ�ʮ��jtf��������jp2���Ժ�������jp1_jl������������
//			System.out.println((String)pfm.getValueAt(0,i));
//			jp2_jtf=new JTextField(20);
//			jp2_jtf.setText((String)pfm.getValueAt(0,i));
//			jp2.add(jp2_jtf);
//		}
//		this.add(jp2,"East");
		
	    //���ߵ�
		jp1=new JPanel(new GridLayout(10,1));
		jp1_jl1=new JLabel(pfm.getColumnName(0));
		jp1_jl2=new JLabel(pfm.getColumnName(1));
		jp1_jl3=new JLabel(pfm.getColumnName(2));
		jp1_jl4=new JLabel(pfm.getColumnName(3));
		jp1_jl5=new JLabel(pfm.getColumnName(4));
		jp1_jl6=new JLabel(pfm.getColumnName(5));
		jp1_jl7=new JLabel(pfm.getColumnName(6));
		jp1_jl8=new JLabel(pfm.getColumnName(7));
		jp1_jl9=new JLabel(pfm.getColumnName(8));
		jp1_jl10=new JLabel(pfm.getColumnName(9));
		//����jp1������jp1���������ڵ�����
		jp1.add(jp1_jl1);
		jp1.add(jp1_jl2);
		jp1.add(jp1_jl3);
		jp1.add(jp1_jl4);
		jp1.add(jp1_jl5);
		jp1.add(jp1_jl6);
		jp1.add(jp1_jl7);
		jp1.add(jp1_jl8);
		jp1.add(jp1_jl9);
		jp1.add(jp1_jl10);
		this.add(jp1,"West");
		//���ߵ�
		jp2=new JPanel(new GridLayout(10,1));
		jp2_jtf1=new JTextField(20);jp2_jtf1.setText((String)pfm.getValueAt(0,0));jp2_jtf1.setEditable(false);
		jp2_jtf2=new JTextField(20);jp2_jtf2.setText((String)pfm.getValueAt(0,1));
		jp2_jtf3=new JTextField(20);jp2_jtf3.setText((String)pfm.getValueAt(0,2));
		jp2_jtf4=new JTextField(20);jp2_jtf4.setText((String)pfm.getValueAt(0,3));
		jp2_jtf5=new JTextField(20);jp2_jtf5.setText((String)pfm.getValueAt(0,4));
		jp2_jtf6=new JTextField(20);jp2_jtf6.setText((String)pfm.getValueAt(0,5));
		jp2_jtf7=new JTextField(20);jp2_jtf7.setText((String)pfm.getValueAt(0,6));
		jp2_jtf8=new JTextField(20);jp2_jtf8.setText((String)pfm.getValueAt(0,7));
		jp2_jtf9=new JTextField(20);jp2_jtf9.setText((String)pfm.getValueAt(0,8));
		jp2_jtf10=new JTextField(20);jp2_jtf10.setText((String)pfm.getValueAt(0,9));
		//����jp2������jp2���������ڵĶ���
		jp2.add(jp2_jtf1);
		jp2.add(jp2_jtf2);
		jp2.add(jp2_jtf3);
		jp2.add(jp2_jtf4);
		jp2.add(jp2_jtf5);
		jp2.add(jp2_jtf6);
		jp2.add(jp2_jtf7);
		jp2.add(jp2_jtf8);
		jp2.add(jp2_jtf9);
		jp2.add(jp2_jtf10);
		this.add(jp2,"East");
		
		//�ϱߵİ�ť
		jp3=new JPanel();
		jp3_jb1=new JButton("ȷ��");jp3_jb1.addActionListener(this);
		jp3_jb2=new JButton("ȡ��");jp3_jb2.addActionListener(this);
		jp3.add(jp3_jb1);
		jp3.add(jp3_jb2);
		this.add(jp3,"South");
		
		//���ô�������
		this.setSize(300,250);
		MyTools.containerLocation(this);
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jp3_jb1){
			//ȷ���޸�
			String[] arrStr={jp2_jtf2.getText(),jp2_jtf3.getText(),jp2_jtf4.getText(),jp2_jtf5.getText(),jp2_jtf6.getText(),jp2_jtf7.getText(),jp2_jtf8.getText(),jp2_jtf9.getText(),jp2_jtf10.getText(),jp2_jtf1.getText()};
			pfm=new PFModel();
			if(!pfm.pfUpd(arrStr)){
				//���ʧ��
				JOptionPane.showMessageDialog(this, "�޸�ʧ��");
			}
			//�ÿ��˳�
			arrStr=null;
			this.dispose();
		}else if(e.getSource()==jp3_jb2){
			//ȡ����ӣ��͹رմ���
			this.dispose();
		}
		
	}
}



