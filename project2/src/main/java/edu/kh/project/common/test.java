package edu.kh.project.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class test {
	public static void main(String[] args) throws ParseException {
		// Date : 날짜용 객체
		// Calender : Date 업그레이드 객체
		// SimpleDateFormat : 날짜를 원하는 형태의 문자열로 변환 
		
		// 오늘 23시 59분 59초까지 남은 시간을 초단위로 구하기 
		
		
		Date b = new Date();
		
		//기준시간 : 1970년 1월 1일 09시 0분 0초
		// new Date(ms) : 기준시간 + ms 만큼 지난 시간
		
		Calendar cal = Calendar.getInstance();
		cal.add(cal.DATE, 1);
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date temp = new Date(cal.getTimeInMillis());
		// 하루 증가한 내일 날짜의 ms 값을 이용해서 Date 객체 생성
		
		
		
		Date a = new Date(); //현재 시간 //Mon Nov 21 12:34:30 KST 2022
		//System.out.println(sdf.format(temp));  차이만큼 쿠키 지속 시간으로 구함
		Date c = sdf.parse(sdf.format(temp)); //Tue Nov 22 00:00:00 KST 2022
		
		System.out.println(a);
		System.out.println(temp);
		System.out.println(c);
		
		// 내일 자정 ms - 현재시간 ms
		long diff = c.getTime() - a.getTime();
		System.out.println(diff/1000-1); //(23:59:59)내일 자정까지 남은 시간(s단위)

		
		
	}
}
