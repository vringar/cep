# Produktvision

Dieses Produkt setzt die Zusammenschaltung zwischen der Workflow-Engine [Zeebe](https://zeebe.io) und der CEP-Engine [Siddhi](https://siddhi.io) um. Hierbei ermöglicht es auf eine einfache Weise CEP-Funktionalitäten in Zeebe-Workflows einzusetzen.  

Workflow-Engines sind Softwaresysteme zur Überwachung und Steuerung von Prozessen basierend auf einem Modell. Während man in der Vergangenheit häufig nur einfache Geschäftsprozesse über Workflows abgewickelt hat, werden nach und nach die Anforderungen in Punkten wie beispielsweise Geschwindigkeit und Parallelität immer höher durch neue Anwendungsgebiete.  
Aus diesen Anwendungsgebieten gehen momentan leistungsfähigere Workflow-Engines, wie Zeebe, hervor. Diese Workflow-Engines sind allerdings auf sehr atomare Formen von Events beschränkt und erfordern somit teils sehr umständliche Modellierungen, um komplexere Zusammenhänge zwischen Events darzustellen.  
Um diese Komplexität besser handhaben zu können, kann man Complex Event Processing (CEP) einsetzen. Dieses ermöglicht es Daten in sinnvolle Informationen umzuwandeln und atomare Events in komplexe Events zu fassen.