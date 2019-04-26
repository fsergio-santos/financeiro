package com.financeiro.util.data;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.stereotype.Component;

@Component
public class DataSistema {
	
	private static int MILISEGUNDOS = 1000; // 1 SEGUNDO
	private static int SEGUNDOS = 60;   // 1 MINUTO
	private static int HORAS = 60;      // 1 HORAS
	private static int DIA   = 24;      // 1 DIA   
	
	
	public DataSistema() {
		
	}

	public Date somaData(Date data ) {  
		Calendar calendario = new GregorianCalendar();
	    calendario.setTime(data);
	    calendario.add(Calendar.DAY_OF_MONTH, + 30 );
		return calendario.getTime(); 
	}
	
	
	public int totalDiasEntreDatas(Date dataInicial, Date dataFinal) {
		return (int) ((dataFinal.getTime() - dataInicial.getTime()) / (MILISEGUNDOS * SEGUNDOS * HORAS * DIA ));
	}
}
