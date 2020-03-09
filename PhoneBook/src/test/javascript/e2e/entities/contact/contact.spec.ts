import { browser, ExpectedConditions as ec, promise } from 'protractor';

import { ContactComponentsPage, ContactDeleteDialog, ContactUpdatePage } from './contact.page-object';

const expect = chai.expect;

describe('Contact e2e test', () => {
  let contactComponentsPage: ContactComponentsPage;
  let contactUpdatePage: ContactUpdatePage;
  let contactDeleteDialog: ContactDeleteDialog;

  before(async () => {
    await browser.get('/');
  });

  it('should load Contacts', async () => {
    contactComponentsPage = new ContactComponentsPage();
    await browser.wait(ec.visibilityOf(contactComponentsPage.title), 5000);
    expect(await contactComponentsPage.getTitle()).to.eq('phonebookApp.contact.home.title');
    await browser.wait(ec.or(ec.visibilityOf(contactComponentsPage.entities), ec.visibilityOf(contactComponentsPage.noResult)), 1000);
  });

  it('should load create Contact page', async () => {
    await contactComponentsPage.clickOnCreateButton();
    contactUpdatePage = new ContactUpdatePage();
    expect(await contactUpdatePage.getPageTitle()).to.eq('phonebookApp.contact.home.createOrEditLabel');
    await contactUpdatePage.cancel();
  });

  it('should create and save Contacts', async () => {
    const nbButtonsBeforeCreate = await contactComponentsPage.countDeleteButtons();

    await contactComponentsPage.clickOnCreateButton();

    await promise.all([
      contactUpdatePage.setFirstNameInput('firstName'),
      contactUpdatePage.setLastNameInput('lastName'),
      contactUpdatePage.setPhoneInput('phone')
    ]);

    expect(await contactUpdatePage.getFirstNameInput()).to.eq('firstName', 'Expected FirstName value to be equals to firstName');
    expect(await contactUpdatePage.getLastNameInput()).to.eq('lastName', 'Expected LastName value to be equals to lastName');
    expect(await contactUpdatePage.getPhoneInput()).to.eq('phone', 'Expected Phone value to be equals to phone');

    await contactUpdatePage.save();
    expect(await contactUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await contactComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Contact', async () => {
    const nbButtonsBeforeDelete = await contactComponentsPage.countDeleteButtons();
    await contactComponentsPage.clickOnLastDeleteButton();

    contactDeleteDialog = new ContactDeleteDialog();
    expect(await contactDeleteDialog.getDialogTitle()).to.eq('phonebookApp.contact.delete.question');
    await contactDeleteDialog.clickOnConfirmButton();

    expect(await contactComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {});
});
