package com.app.service;

import com.app.gst.InvoiceLinedata;
import com.axelor.gst.db.Address;
import com.axelor.gst.db.Contact;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.Invoiceline;
import com.axelor.gst.db.Party;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ServiceImpl implements ServiceData {

  @Override
  public Invoice setInvoiceDataService(Invoice invoice, ArrayList<Invoiceline> il) {
    try {
      BigDecimal cgst = BigDecimal.ZERO;
      BigDecimal sgst = BigDecimal.ZERO;
      BigDecimal igst = BigDecimal.ZERO;
      BigDecimal netTotal = BigDecimal.ZERO;
      BigDecimal grossTotal = BigDecimal.ZERO;

      for (Invoiceline invoiceLine : il) {
        netTotal = netTotal.add(invoiceLine.getNetAmount());
        sgst = sgst.add(invoiceLine.getSgst());
        cgst = cgst.add(invoiceLine.getCgst());
        igst = igst.add(invoiceLine.getIgst());
        grossTotal = grossTotal.add(invoiceLine.getGrossAmount());
      }

      invoice.setNetIgst(igst);
      invoice.setNetCsgst(cgst);
      invoice.setNetSgst(sgst);
      invoice.setNetAmount(netTotal);
      invoice.setGrossAmount(grossTotal);

    } catch (Exception e) {
      // TODO: handle exception
    }

    return invoice;
  }

  @Override
  public Invoiceline setInvoicelineData(Invoice invoice, Invoiceline invoiceline) {
    try {
      BigDecimal n;
      Address companyAddress = invoice.getCompany().getAddress();
      Address invoiceAddress = invoice.getInvoiceAddress();

      BigDecimal netamount, netigst, netcsgst, netsgst, grossamount = new BigDecimal(0);
      BigDecimal b = new BigDecimal(invoiceline.getQty());
      netamount = invoiceline.getPrice().multiply(b);

      if (companyAddress.getState().equals(invoiceAddress.getState())) {
        netigst = new BigDecimal(0);
        netcsgst =
            netamount
                .multiply(invoiceline.getGstrate().divide(new BigDecimal(100)))
                .divide(new BigDecimal(2));
        netsgst =
            netamount
                .multiply(invoiceline.getGstrate().divide(new BigDecimal(100)))
                .divide(new BigDecimal(2));
      } else {
        netcsgst = new BigDecimal(0);
        netsgst = new BigDecimal(0);
        netigst =
            netamount
                .multiply(invoiceline.getGstrate().divide(new BigDecimal(100)))
                .divide(new BigDecimal(2));
      }

      grossamount = grossamount.add(netamount).add(netcsgst).add(netigst).add(netsgst);
      //	System.err.println(grossamount);
      invoiceline.setNetAmount(netamount);
      invoiceline.setIgst(netigst);
      invoiceline.setCgst(netcsgst);
      invoiceline.setSgst(netsgst);
      invoiceline.setGrossAmount(grossamount);
    } catch (Exception e) {
      // TODO: handle exception
    }

    return invoiceline;
  }

  @Override
  public Invoice setPartyData(Invoice invoice) {

    try {
      Party party = invoice.getParty();
      if (party != null) {
        List<Contact> contactList = party.getContactList();
        if (contactList != null && !contactList.isEmpty()) {
          for (Contact contact : contactList) {
            if (contact.getType().equals("Primary")) {
              invoice.setPartyContact(contact);
              break;
            }
          }

          List<Address> addressList = party.getAddressList();
          for (Address address : addressList) {
            if (address.getType().equals("default") || address.getType().equals("invoice")) {
              invoice.setInvoiceAddress(address);
              break;
            }
          }
          if (!invoice.getIsUseInVoiceAddressAsShipping()) {
            for (Address address : addressList) {
              if (address.getType().equals("default") || address.getType().equals("shipping")) {
                invoice.setShippingAddress(address);
                break;
              }
            }
          } else {
            for (Address address : addressList) {
              if (address.getType().equals("default") || address.getType().equals("invoice")) {
                invoice.setShippingAddress(address);
                break;
              }
            }
          }
        }
      }

    } catch (Exception e) {
      // TODO: handle exception
      System.out.println("Method:setinvoiceDefaultfield");
    }
    return invoice;
  }

  public Invoiceline setPartyCompanyChangeData(Invoice invoice, Invoiceline invoiceline) {
    try {
      InvoiceLinedata invoicelinedata = new InvoiceLinedata();
      BigDecimal n;
      Address companyAddress = invoice.getCompany().getAddress();
      Address invoiceAddress = invoice.getInvoiceAddress();

      BigDecimal netamount, netigst, netcsgst, netsgst, grossamount = new BigDecimal(0);
      BigDecimal b = new BigDecimal(invoiceline.getQty());
      netamount = invoiceline.getPrice().multiply(b);

      if (companyAddress.getState().equals(invoiceAddress.getState())) {
        netigst = new BigDecimal(0);
        netcsgst =
            netamount
                .multiply(invoiceline.getGstrate().divide(new BigDecimal(100)))
                .divide(new BigDecimal(2));
        netsgst =
            netamount
                .multiply(invoiceline.getGstrate().divide(new BigDecimal(100)))
                .divide(new BigDecimal(2));
      } else {
        netcsgst = new BigDecimal(0);
        netsgst = new BigDecimal(0);
        netigst =
            netamount
                .multiply(invoiceline.getGstrate().divide(new BigDecimal(100)))
                .divide(new BigDecimal(2));
      }

      grossamount = grossamount.add(netamount).add(netcsgst).add(netigst).add(netsgst);
      //	System.err.println(grossamount);
      invoiceline.setNetAmount(netamount);
      invoiceline.setIgst(netigst);
      invoiceline.setCgst(netcsgst);
      invoiceline.setSgst(netsgst);
      invoiceline.setGrossAmount(grossamount);
      invoicelinedata.persist(invoiceline);
    } catch (Exception e) {
      // TODO: handle exception
    }

    return invoiceline;
  }
}
