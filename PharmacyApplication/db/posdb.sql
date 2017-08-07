--ENTERPRISE
CREATE TABLE [dbo].[TblEnterprise](
	[EnterpriseID] [int] IDENTITY(1,1) NOT NULL,
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
	[SU_SYSUSERID] [numeric](5, 0) IDENTITY(1,1) NOT NULL,
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

CREATE TABLE [dbo].[POS_TBLCUSTOMERS](
	[CUS_ID] [numeric](5, 0) IDENTITY(1,1) NOT NULL,
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