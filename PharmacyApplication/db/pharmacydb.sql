USE [PharmacyDB]
GO

/****** Object:  Table [dbo].[TblEnterprise]    Script Date: 7/10/2017 1:11:12 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

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
	[ModifyDate] [date] NULL,
PRIMARY KEY CLUSTERED 
(
	[EnterpriseID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[TblEnterprise] ADD  DEFAULT ((0)) FOR [isDemo]
GO

ALTER TABLE [dbo].[TblEnterprise] ADD  DEFAULT (getdate()) FOR [InsertDate]
GO

ALTER TABLE [dbo].[TblEnterprise] ADD  DEFAULT (getdate()) FOR [ModifyDate]
GO



CREATE TABLE SS_TBLSYSUSERS
(
  SU_SYSUSERID               numeric(5)          NOT NULL Primary Key IDENTITY(1,1),
  ENTERPRISEID               numeric(8)          NOT NULL,
  SU_SYSUSERNAME             nvarchar(50)  DEFAULT '',
  SU_SYSLOGIN                nvarchar(50)  NOT NULL,
  SU_SYSPASSWORD             nvarchar(50)  NOT NULL,
  SU_ISSUPERADMIN            numeric(1)          DEFAULT (0)                   NOT NULL,
  SU_SYSUSEREMAIL            nvarchar(50)  DEFAULT '',
  REG_INSERTDATE             DATE               DEFAULT GETDATE()             NOT NULL,
  REG_MODIFYDATE             DATE               DEFAULT GETDATE()             NOT NULL,
  SU_ISMANAGER          	 numeric(1)          DEFAULT 0,
)