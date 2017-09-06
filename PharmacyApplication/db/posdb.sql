--ENTERPRISE
CREATE TABLE [dbo].[TblEnterprise](
	[EnterpriseID] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
	[EnterpriseName] [nvarchar](100) NOT NULL,
	[LoginName] [nvarchar](100) NOT NULL,
	[isDemo] [int] NOT NULL,
	[Address] [nvarchar](100) NULL,
	[City] [nvarchar](200) NULL,
	[State] [nvarchar](200) NULL,
	[Country] [nvarchar](200) NULL,
	[ZipCode] [nvarchar](100) NULL,
	[Phone] [nvarchar](100) NULL,
	[Fax] [nvarchar](100) NULL,
	[Cell] [nvarchar](100) NULL,
	[Email] [nvarchar](100) NULL,
	[WebSite] [nvarchar](100) NULL,
	[Brand] [nvarchar](100) NULL,
	[ExpiryDate] [date] NULL,
	[Message] [nvarchar](200) NULL,
	[Response] [nvarchar](200) NULL,
	[InsertDate] [date] NULL,
	[ModifyDate] [date] NULL
)

--SYSUSERS
CREATE TABLE [dbo].[SS_TBLSYSUSERS](
	[SU_SYSUSERID] [numeric](5, 0) IDENTITY(1,1) NOT NULL PRIMARY KEY,
	[ENTERPRISEID] [numeric](8, 0) NOT NULL,
	[SU_SYSUSERNAME] [nvarchar](50) NULL,
	[SU_SYSLOGIN] [nvarchar](50) NOT NULL,
	[SU_SYSPASSWORD] [nvarchar](50) NOT NULL,
	[SU_ISSUPERADMIN] [numeric](1, 0) NOT NULL,
	[SU_SYSUSEREMAIL] [nvarchar](50) NULL,
	[REG_INSERTDATE] [date] NOT NULL,
	[REG_MODIFYDATE] [date] NOT NULL,
	[SU_ISMANAGER] [numeric](1, 0) NULL
)

--POS_TBLCUSTOMERS
CREATE TABLE [dbo].[POS_TBLCUSTOMERS](
	[CUS_ID] [numeric](5, 0) IDENTITY(1,1) NOT NULL PRIMARY KEY,
	[ENTERPRISEID] [numeric](8, 0) NOT NULL,
	[CUS_FNAME] [nvarchar](100) NULL,
	[CUS_LNAME] [nvarchar](100) NULL,
	[CUS_EMAIL] [nvarchar](50) NULL,
	[CUS_PHONENUMBER] [nvarchar](50) NULL,
	[CUS_ADDRESS1] [nvarchar](100) NULL,
	[CUS_ADDRESS2] [nvarchar](100) NULL,
	[CUS_PROVINCE] [nvarchar](50) NULL,
	[CUS_ZIP] [nvarchar](50) NULL,
	[CUS_COUNTRY] [nvarchar](100) NULL,
	[CUS_COMMENTS] [nvarchar](100) NULL,
	[CUS_INSERTDATE] [date] NOT NULL,
	[CUS_MODIFYDATE] [date] NOT NULL,
)

--POS_TBLSUPPLIERS
CREATE TABLE [dbo].[POS_TBLSUPPLIERS](
	[SUP_ID] [numeric](5, 0) IDENTITY(1,1) NOT NULL PRIMARY KEY,
	[ENTERPRISEID] [numeric](8, 0) NOT NULL,
	[SUP_FNAME] [nvarchar](100) NULL,
	[SUP_LNAME] [nvarchar](100) NULL,
	[SUP_CODE] [nvarchar](100) NULL UNIQUE,
	[SUP_EMAIL] [nvarchar](50) NULL,
	[SUP_PHONENUMBER] [nvarchar](50) NULL,
	[SUP_ADDRESS1] [nvarchar](100) NULL,
	[SUP_ADDRESS2] [nvarchar](100) NULL,
	[SUP_PROVINCE] [nvarchar](50) NULL,
	[SUP_ZIP] [nvarchar](50) NULL,
	[SUP_COUNTRY] [nvarchar](100) NULL,
	[SUP_INSERTDATE] [date] NOT NULL,
	[SUP_MODIFYDATE] [date] NOT NULL,
)

--POS_TBLPRODUCTTYPE
CREATE TABLE [dbo].[POS_TBLPRODUCTTYPE](
	[PT_ID] [numeric](5, 0) IDENTITY(1,1) NOT NULL PRIMARY KEY,
	[ENTERPRISEID] [numeric](8, 0) NOT NULL,
	[PT_NAME] [nvarchar](100) NULL,
	[PT_INSERTDATE] [date] NOT NULL,
	[PT_MODIFYDATE] [date] NOT NULL,
)

--POS_TBLPRODUCTS
CREATE TABLE [dbo].[POS_TBLPRODUCTS](
	[PR_ID] [numeric](5, 0) IDENTITY(1,1) NOT NULL PRIMARY KEY,
	[ENTERPRISEID] [numeric](8, 0) NOT NULL,
	[PR_NAME] [nvarchar](100) NULL,
	[PR_CODE] [nvarchar](100) NULL UNIQUE,
	[PR_PRODUCTTYPEID] [numeric](5, 0) NOT NULL,
	[PR_SUPPLIERID] [numeric](5, 0) NOT NULL,
	[PR_SELLINGPRICE] [float] NOT NULL,
	[PR_PROQUANTITY] [float] NOT NULL,
	[PR_BUYINGPRICE] [float]NOT NULL,
	[PR_INSERTDATE] [date] NOT NULL,
	[PR_MODIFYDATE] [date] NOT NULL,
)


--POS_TBLBILL
CREATE TABLE [dbo].[POS_TBLBILL](
	[BILL_ID] [numeric](5, 0) NOT NULL PRIMARY KEY,
	[ENTERPRISEID] [numeric](8, 0) NOT NULL,
	[BILL_CUSID] [numeric](8, 0) NULL,
	[BILL_AMOUNT] [float] NOT NULL,
	[BILL_DISCOUNT] [float] NOT NULL,
	[BILL_DUEAMOUNT] [float]NOT NULL,
	[BILL_INSERTDATE] [date] NOT NULL,
	[BILL_MODIFYDATE] [date] NOT NULL,
)

ALTER TABLE [dbo].[POS_TBLBILL] ADD [BILL_NAME] [nvarchar](100) NOT NULL

--POS_TBLBILLLINE
CREATE TABLE [dbo].[POS_TBLBILLLINE](
	[BL_ID] [numeric](5, 0) NOT NULL PRIMARY KEY IDENTITY(1,1),
	[ENTERPRISEID] [numeric](8, 0) NOT NULL,
	[BL_BILLID] [numeric](5, 0) NOT NULL,
	[BL_PROID] [numeric](5, 0) NOT NULL,
	[BL_AMOUNT] [float] NOT NULL,
	[BL_DISCOUNT] [float] NOT NULL,
	[BL_DUEAMOUNT] [float]NOT NULL,
	[BL_INSERTDATE] [date] NOT NULL,
	[BL_MODIFYDATE] [date] NOT NULL,
)

ALTER TABLE [dbo].[POS_TBLBILLLINE] ADD [BL_PROQuantity] [int] NOT NULL
ALTER TABLE [dbo].[POS_TBLBILLLINE] ADD [BL_PROPrice] [float] NOT NULL


--POS_TBLPURCHASE
CREATE TABLE [dbo].[POS_TBLPURCHASE](
	[PUR_ID] [numeric](5, 0) NOT NULL PRIMARY KEY IDENTITY(1,1),
	[ENTERPRISEID] [numeric](8, 0) NOT NULL,
	[PUR_PROID] [numeric](5, 0) NOT NULL,
	[PUR_SUPID] [numeric](5, 0) NOT NULL,
	[PUR_Quantity] [float] NOT NULL,
	[PUR_INSERTDATE] [date] NOT NULL,
	[PUR_MODIFYDATE] [date] NOT NULL,
)

ALTER TABLE [dbo].[POS_TBLPURCHASE] ADD [PUR_OLDQuantity] [float] DEFAULT (0.0) NOT NULL
ALTER TABLE [dbo].[POS_TBLPURCHASE] ADD [PUR_BUYINGPRICE] [float] DEFAULT (0.0) NOT NULL