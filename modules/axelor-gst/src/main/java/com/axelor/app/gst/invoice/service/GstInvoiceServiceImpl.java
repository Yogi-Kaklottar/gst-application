package com.axelor.app.gst.invoice.service;

import com.axelor.gst.db.Address;
import com.axelor.gst.db.Contact;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import com.axelor.gst.db.Party;
import com.google.inject.persist.Transactional;
import java.math.BigDecimal;
import java.util.List;

public class GstInvoiceServiceImpl implements GstInvoiceService {

  @Override
  public Invoice setInvoicePartySelectDefault(Invoice invoice) {
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
      // System.out.println("Method:setinvoiceDefaultfield");
    }
    return invoice;
  }

  @Override
  public Invoice setInvoiceComputation(Invoice invoice, List<InvoiceLine> invoiceLineList) {
    try {
      BigDecimal cgst = BigDecimal.ZERO;
      BigDecimal sgst = BigDecimal.ZERO;
      BigDecimal igst = BigDecimal.ZERO;
      BigDecimal netTotal = BigDecimal.ZERO;
      BigDecimal grossTotal = BigDecimal.ZERO;

      for (InvoiceLine invoiceLine : invoiceLineList) {
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
      //  System.err.println("InvoiceComputation");
    }

    return invoice;
  }

  @Override
  @Transactional
  public InvoiceLine setComputationPartyCompanyChange(Invoice invoice, InvoiceLine invoiceLine) {
    try {

      BigDecimal n;
      Address companyAddress = invoice.getCompany().getAddress();
      Address invoiceAddress = invoice.getInvoiceAddress();

      BigDecimal netamount, netigst, netcsgst, netsgst, grossamount = new BigDecimal(0);
      BigDecimal b = new BigDecimal(invoiceLine.getQty());
      netamount = invoiceLine.getPrice().multiply(b);

      if (companyAddress.getState().equals(invoiceAddress.getState())) {
        netigst = new BigDecimal(0);
        netcsgst =
            netamount
                .multiply(invoiceLine.getGstRate().divide(new BigDecimal(100)))
                .divide(new BigDecimal(2));
        netsgst =
            netamount
                .multiply(invoiceLine.getGstRate().divide(new BigDecimal(100)))
                .divide(new BigDecimal(2));
      } else {
        netcsgst = new BigDecimal(0);
        netsgst = new BigDecimal(0);
        netigst = netamount.multiply(invoiceLine.getGstRate().divide(new BigDecimal(100)));
      }

      grossamount = grossamount.add(netamount).add(netcsgst).add(netigst).add(netsgst);

      invoiceLine.setNetAmount(netamount);
      invoiceLine.setIgst(netigst);
      invoiceLine.setCgst(netcsgst);
      invoiceLine.setSgst(netsgst);
      invoiceLine.setGrossAmount(grossamount);
      // gstInvoiceLineRepository.save(invoiceLine);

    } catch (Exception e) {
      // TODO: handle exception
      //  System.err.println(e.fillInStackTrace());
    }

    return invoiceLine;
  }

  @Override
  public Invoice setAsBooleanValueShippingAddress(Invoice invoice) {
    try {
      Party party = invoice.getParty();
      if (party == null) {
        throw new Exception();
      }
      List<Address> addressList = party.getAddressList();
      if (addressList.size() < 1) {
        if (!invoice.getIsUseInVoiceAddressAsShipping()) {
          for (Address address : addressList) {
            if (address.getType().equals("default") || address.getType().equals("shipping")) {
              invoice.setShippingAddress(address);
              System.out.println("hhhhh");
              break;
            }
          }
        }
      }
    } catch (Exception e) {
      System.out.println(e.fillInStackTrace());
    }
    return invoice;
  }
}
