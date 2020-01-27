package com.app.gst;

import com.app.service.ServiceData;
import com.axelor.gst.db.Address;
import com.axelor.gst.db.Contact;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.Invoiceline;
import com.axelor.gst.db.Party;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.bcel.generic.IfInstruction;

public class InvoicelineData {

	@Inject
	ServiceData setData;

	public void setInvoiceData(ActionRequest request, ActionResponse response) {
		try {
			Invoice invoice = request.getContext().asType(Invoice.class);
			ArrayList<Invoiceline> il = (ArrayList<Invoiceline>) invoice.getInvoiceitemList();
			invoice = setData.setInvoiceDataService(invoice, il);
			response.setValues(invoice);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Method:setInvoiceData");
		}

	}

	public void setInvoiceLineData(ActionRequest request, ActionResponse response) {
		try
		{
		Invoiceline invoiceline = request.getContext().asType(Invoiceline.class);
		Invoice invoice = request.getContext().getParent().asType(Invoice.class);
		invoiceline=setData.setInvoicelineData(invoice, invoiceline);
		response.setValues(invoiceline);
		}
		catch (Exception e) {
			System.out.println("Method:setInvoiceLineData");
		}
	}

	public void setinvoiceDefaultfield(ActionRequest request, ActionResponse respons) {
		try
		{
			Invoice invoice = request.getContext().asType(Invoice.class);
			invoice=setData.setPartyData(invoice);
			respons.setValues(invoice);
		}
		catch (Exception e) {
			System.out.println("Method:setinvoiceDefaultfield");
		}
		
	}
	public void setInvoicelinePartyChange(ActionRequest request, ActionResponse respons) {
		try
		{
			Invoice invoice = request.getContext().asType(Invoice.class);
			if(invoice!=null)
			{
					if(invoice.getInvoiceitemList().size()==0)
						{
						
						}
					else
						{
						  ArrayList<Invoiceline> il=(ArrayList<Invoiceline>) invoice.getInvoiceitemList();
						 
						  for(Invoiceline i:il)
						  {
							  
							 i=setData.setPartyCompanyChangeData(invoice,i);
						//	 System.err.println(i);						 
							
							 			 
						  }
						  invoice=setData.setInvoiceDataService(invoice, il);
						}
			}
			respons.setValues(invoice);
		}
		catch (Exception e) {
			//System.out.println("Method:setInvoicelinePartyChange");
		}
		
	}
	
}
