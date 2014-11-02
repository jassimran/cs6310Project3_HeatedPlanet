
/****** Object:  Table [dbo].[Simulations]    Script Date: 11/2/2014 8:20:15 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[Simulations](
	[SimulationID] [bigint] IDENTITY(1,1) NOT NULL,
	[Name] [varchar](200) NULL,
 CONSTRAINT [PK_Simulations] PRIMARY KEY CLUSTERED 
(
	[SimulationID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO



/****** Object:  Table [dbo].[SimulationSettings]    Script Date: 11/2/2014 8:20:22 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[SimulationSettings](
	[SimulationSettingsID] [bigint] IDENTITY(1,1) NOT NULL,
	[SimulationID] [bigint] NOT NULL,
	[SimulationTimeStep] [int] NOT NULL,
	[SimulationLength] [int] NOT NULL,
 CONSTRAINT [PK_SimulationSettings] PRIMARY KEY CLUSTERED 
(
	[SimulationSettingsID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[SimulationSettings]  WITH CHECK ADD  CONSTRAINT [FK_SimulationSettings_Simulations] FOREIGN KEY([SimulationID])
REFERENCES [dbo].[Simulations] ([SimulationID])
GO

ALTER TABLE [dbo].[SimulationSettings] CHECK CONSTRAINT [FK_SimulationSettings_Simulations]
GO




/****** Object:  Table [dbo].[InvocationParameters]    Script Date: 11/2/2014 8:18:25 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[InvocationParameters](
	[InvocationParametersID] [bigint] IDENTITY(1,1) NOT NULL,
	[SimulationID] [bigint] NOT NULL,
	[Precision] [int] NOT NULL,
	[GeographicAccuracy] [int] NOT NULL,
	[TemporalAcuuracy] [int] NOT NULL,
 CONSTRAINT [PK_InvocationParameters] PRIMARY KEY CLUSTERED 
(
	[InvocationParametersID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[InvocationParameters]  WITH CHECK ADD  CONSTRAINT [FK_InvocationParameters_Simulations] FOREIGN KEY([SimulationID])
REFERENCES [dbo].[Simulations] ([SimulationID])
GO

ALTER TABLE [dbo].[InvocationParameters] CHECK CONSTRAINT [FK_InvocationParameters_Simulations]
GO




/****** Object:  Table [dbo].[PhysicalFactors]    Script Date: 11/2/2014 8:19:43 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[PhysicalFactors](
	[PhysicalFactorsID] [bigint] IDENTITY(1,1) NOT NULL,
	[SimulationID] [bigint] NOT NULL,
	[AxialTilt] [int] NOT NULL,
	[OrbitalEccentricity] [real] NOT NULL,
 CONSTRAINT [PK_PhysicalFactors] PRIMARY KEY CLUSTERED 
(
	[PhysicalFactorsID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[PhysicalFactors]  WITH CHECK ADD  CONSTRAINT [FK_PhysicalFactors_Simulations] FOREIGN KEY([SimulationID])
REFERENCES [dbo].[Simulations] ([SimulationID])
GO

ALTER TABLE [dbo].[PhysicalFactors] CHECK CONSTRAINT [FK_PhysicalFactors_Simulations]
GO




/****** Object:  Table [dbo].[TimeSteps]    Script Date: 11/2/2014 8:20:31 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[TimeSteps](
	[TimeStepID] [bigint] IDENTITY(1,1) NOT NULL,
	[SimulationID] [bigint] NOT NULL,
	[TimeStepIndex] [int] NOT NULL,
 CONSTRAINT [PK_TimeSteps] PRIMARY KEY CLUSTERED 
(
	[TimeStepID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[TimeSteps]  WITH CHECK ADD  CONSTRAINT [FK_TimeSteps_Simulations] FOREIGN KEY([SimulationID])
REFERENCES [dbo].[Simulations] ([SimulationID])
GO

ALTER TABLE [dbo].[TimeSteps] CHECK CONSTRAINT [FK_TimeSteps_Simulations]
GO



/****** Object:  Table [dbo].[Nodes]    Script Date: 11/2/2014 8:19:08 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Nodes](
	[NodeID] [bigint] IDENTITY(1,1) NOT NULL,
	[TimeStepID] [bigint] NOT NULL,
	[Latitude] [real] NOT NULL,
	[Longitude] [real] NOT NULL,
	[Temperature] [real] NOT NULL,
	[ReadingDate] [date] NOT NULL,
	[ReadingTime] [time](7) NOT NULL,
 CONSTRAINT [PK_Nodes] PRIMARY KEY CLUSTERED 
(
	[NodeID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[Nodes]  WITH CHECK ADD  CONSTRAINT [FK_Nodes_TimeSteps] FOREIGN KEY([TimeStepID])
REFERENCES [dbo].[TimeSteps] ([TimeStepID])
GO

ALTER TABLE [dbo].[Nodes] CHECK CONSTRAINT [FK_Nodes_TimeSteps]
GO


