
// ---------------------------------------------------------------------------
// This example is for use with spark 1.2 CDH 5.3, it processes MNIST images
// using deep learning from h2o.
// ---------------------------------------------------------------------------

// import packages

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD


object h2o_spark_ex3  extends App
{
  // ---------------------------------------------------------------------------
  // create a schema for a row of integers that represents an image 28x28 plus 
  // an integer element to label the row.

  def getSchema(): String = {

    var schema = ""
    val limit = 28*28

    for (i <- 1 to limit){
      schema += "P" + i.toString + " " 
    }
    schema += "Label"

    schema // return value

  }
  // ---------------------------------------------------------------------------

  // create  a spark conf and context

  val sparkMaster = "spark://hc2nn.semtech-solutions.co.nz:7077"
  val appName = "Spark ex3"
  val conf = new SparkConf()

  conf.setMaster(sparkMaster)
  conf.setAppName(appName)

  val sparkCxt = new SparkContext(conf)


  // Initialize SQL context

  import org.apache.spark.sql._
  implicit val sqlContext = new SQLContext(sparkCxt)

  // prep the hdfs based data

  val server    = "hdfs://hc2nn.semtech-solutions.co.nz:8020"
  val path      = "/data/spark/h2o/"

  val train_csv =  server + path + "mnist_train_1x.csv"

  val rawTrainData = sparkCxt.textFile(train_csv)

  val schemaString = getSchema() // string representing schema 28 x 28 int plus label int

  val schema = StructType( schemaString.split(" ")
         .map(fieldName => StructField(fieldName, IntegerType, false)))

  val trainRDD  = rawTrainData.map( rawRow => rawRow.split(",")) 
        .map( p => Row( 

p(0),p(1),p(2),p(3),p(4),p(5),p(6),p(7),p(8),p(9),
p(10),p(11),p(12),p(13),p(14),p(15),p(16),p(17),p(18),p(19),
p(20),p(21),p(22),p(23),p(24),p(25),p(26),p(27),p(28),p(29),
p(30),p(31),p(32),p(33),p(34),p(35),p(36),p(37),p(38),p(39),
p(40),p(41),p(42),p(43),p(44),p(45),p(46),p(47),p(48),p(49),
p(50),p(51),p(52),p(53),p(54),p(55),p(56),p(57),p(58),p(59),
p(60),p(61),p(62),p(63),p(64),p(65),p(66),p(67),p(68),p(69),
p(70),p(71),p(72),p(73),p(74),p(75),p(76),p(77),p(78),p(79),
p(80),p(81),p(82),p(83),p(84),p(85),p(86),p(87),p(88),p(89),
p(90),p(91),p(92),p(93),p(94),p(95),p(96),p(97),p(98),p(99),
p(100),p(101),p(102),p(103),p(104),p(105),p(106),p(107),p(108),p(109),
p(110),p(111),p(112),p(113),p(114),p(115),p(116),p(117),p(118),p(119),
p(120),p(121),p(122),p(123),p(124),p(125),p(126),p(127),p(128),p(129),
p(130),p(131),p(132),p(133),p(134),p(135),p(136),p(137),p(138),p(139),
p(140),p(141),p(142),p(143),p(144),p(145),p(146),p(147),p(148),p(149),
p(150),p(151),p(152),p(153),p(154),p(155),p(156),p(157),p(158),p(159),
p(160),p(161),p(162),p(163),p(164),p(165),p(166),p(167),p(168),p(169),
p(170),p(171),p(172),p(173),p(174),p(175),p(176),p(177),p(178),p(179),
p(180),p(181),p(182),p(183),p(184),p(185),p(186),p(187),p(188),p(189),
p(190),p(191),p(192),p(193),p(194),p(195),p(196),p(197),p(198),p(199),
p(200),p(201),p(202),p(203),p(204),p(205),p(206),p(207),p(208),p(209),
p(210),p(211),p(212),p(213),p(214),p(215),p(216),p(217),p(218),p(219),
p(220),p(221),p(222),p(223),p(224),p(225),p(226),p(227),p(228),p(229),
p(230),p(231),p(232),p(233),p(234),p(235),p(236),p(237),p(238),p(239),
p(240),p(241),p(242),p(243),p(244),p(245),p(246),p(247),p(248),p(249),
p(250),p(251),p(252),p(253),p(254),p(255),p(256),p(257),p(258),p(259),
p(260),p(261),p(262),p(263),p(264),p(265),p(266),p(267),p(268),p(269),
p(270),p(271),p(272),p(273),p(274),p(275),p(276),p(277),p(278),p(279),
p(280),p(281),p(282),p(283),p(284),p(285),p(286),p(287),p(288),p(289),
p(290),p(291),p(292),p(293),p(294),p(295),p(296),p(297),p(298),p(299),
p(300),p(301),p(302),p(303),p(304),p(305),p(306),p(307),p(308),p(309),
p(310),p(311),p(312),p(313),p(314),p(315),p(316),p(317),p(318),p(319),
p(320),p(321),p(322),p(323),p(324),p(325),p(326),p(327),p(328),p(329),
p(330),p(331),p(332),p(333),p(334),p(335),p(336),p(337),p(338),p(339),
p(340),p(341),p(342),p(343),p(344),p(345),p(346),p(347),p(348),p(349),
p(350),p(351),p(352),p(353),p(354),p(355),p(356),p(357),p(358),p(359),
p(360),p(361),p(362),p(363),p(364),p(365),p(366),p(367),p(368),p(369),
p(370),p(371),p(372),p(373),p(374),p(375),p(376),p(377),p(378),p(379),
p(380),p(381),p(382),p(383),p(384),p(385),p(386),p(387),p(388),p(389),
p(390),p(391),p(392),p(393),p(394),p(395),p(396),p(397),p(398),p(399),
p(400),p(401),p(402),p(403),p(404),p(405),p(406),p(407),p(408),p(409),
p(410),p(411),p(412),p(413),p(414),p(415),p(416),p(417),p(418),p(419),
p(420),p(421),p(422),p(423),p(424),p(425),p(426),p(427),p(428),p(429),
p(430),p(431),p(432),p(433),p(434),p(435),p(436),p(437),p(438),p(439),
p(440),p(441),p(442),p(443),p(444),p(445),p(446),p(447),p(448),p(449),
p(450),p(451),p(452),p(453),p(454),p(455),p(456),p(457),p(458),p(459),
p(460),p(461),p(462),p(463),p(464),p(465),p(466),p(467),p(468),p(469),
p(470),p(471),p(472),p(473),p(474),p(475),p(476),p(477),p(478),p(479),
p(480),p(481),p(482),p(483),p(484),p(485),p(486),p(487),p(488),p(489),
p(490),p(491),p(492),p(493),p(494),p(495),p(496),p(497),p(498),p(499),
p(500),p(501),p(502),p(503),p(504),p(505),p(506),p(507),p(508),p(509),
p(510),p(511),p(512),p(513),p(514),p(515),p(516),p(517),p(518),p(519),
p(520),p(521),p(522),p(523),p(524),p(525),p(526),p(527),p(528),p(529),
p(530),p(531),p(532),p(533),p(534),p(535),p(536),p(537),p(538),p(539),
p(540),p(541),p(542),p(543),p(544),p(545),p(546),p(547),p(548),p(549),
p(550),p(551),p(552),p(553),p(554),p(555),p(556),p(557),p(558),p(559),
p(560),p(561),p(562),p(563),p(564),p(565),p(566),p(567),p(568),p(569),
p(570),p(571),p(572),p(573),p(574),p(575),p(576),p(577),p(578),p(579),
p(580),p(581),p(582),p(583),p(584),p(585),p(586),p(587),p(588),p(589),
p(590),p(591),p(592),p(593),p(594),p(595),p(596),p(597),p(598),p(599),
p(600),p(601),p(602),p(603),p(604),p(605),p(606),p(607),p(608),p(609),
p(610),p(611),p(612),p(613),p(614),p(615),p(616),p(617),p(618),p(619),
p(620),p(621),p(622),p(623),p(624),p(625),p(626),p(627),p(628),p(629),
p(630),p(631),p(632),p(633),p(634),p(635),p(636),p(637),p(638),p(639),
p(640),p(641),p(642),p(643),p(644),p(645),p(646),p(647),p(648),p(649),
p(650),p(651),p(652),p(653),p(654),p(655),p(656),p(657),p(658),p(659),
p(660),p(661),p(662),p(663),p(664),p(665),p(666),p(667),p(668),p(669),
p(670),p(671),p(672),p(673),p(674),p(675),p(676),p(677),p(678),p(679),
p(680),p(681),p(682),p(683),p(684),p(685),p(686),p(687),p(688),p(689),
p(690),p(691),p(692),p(693),p(694),p(695),p(696),p(697),p(698),p(699),
p(700),p(701),p(702),p(703),p(704),p(705),p(706),p(707),p(708),p(709),
p(710),p(711),p(712),p(713),p(714),p(715),p(716),p(717),p(718),p(719),
p(720),p(721),p(722),p(723),p(724),p(725),p(726),p(727),p(728),p(729),
p(730),p(731),p(732),p(733),p(734),p(735),p(736),p(737),p(738),p(739),
p(740),p(741),p(742),p(743),p(744),p(745),p(746),p(747),p(748),p(749),
p(750),p(751),p(752),p(753),p(754),p(755),p(756),p(757),p(758),p(759),
p(760),p(761),p(762),p(763),p(764),p(765),p(766),p(767),p(768),p(769),
p(770),p(771),p(772),p(773),p(774),p(775),p(776),p(777),p(778),p(779),
p(780),p(781),p(782),p(783),p(784)

                   )
            )

//  val rowData  = rawTrainData.map( rawRow => Row( rawRow.split(",") .map(_.toInt) ) ).collect
//  val trainRDD = sparkCxt.parallelize(rowData)

  val trainSchemaRDD = sqlContext.applySchema(trainRDD, schema)

  trainSchemaRDD.registerTempTable("trainingTable")

  println( " >>>>> B4 sql " )

  sqlContext.sql("SELECT P1,P2,Label FROM trainingTable").collect().foreach(println)

//  val schemaRddTrain = sql("""SELECT P1,Label FROM trainingTable""".stripMargin)

//  println( " >>>>> B4 result " )

//  println( " >>>>> sqlResult rows = " + resSchemaRddTrain.count() )

//  println( " >>>>> B4 collect " )

  // val resArray = resSchemaRddTrain.collect()
  // collect results in  java.lang.ArrayIndexOutOfBoundsException

//  resArray.foreach( resRow => 
//    { println( "Result row length = " + resRow.length ) }
//  )

//  resSchemaRddTrain.collect().foreach(row => {

//	  val f0 = if(row.isNullAt(0)) "null" else row.getInt(0)
//
//	  println(s"result:$f0")
//	})



  println( " >>>>> Script Finished <<<<< " )

} // end application
