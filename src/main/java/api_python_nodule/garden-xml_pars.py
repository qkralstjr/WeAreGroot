import requests
from bs4 import BeautifulSoup
import xml.etree.ElementTree as ET
import xmltodict
# import oracledb # oracledb 라이브러리 임포트(불러오기)
import pyodbc
import json
import numpy as np

try:
    # DB에 연결 (호스트이름 대신 IP주소 가능)
    db = "xe"
    con = pyodbc.connect(
        driver="{MYSQL ODBC 8.4 Unicode Driver}",
        server="mydb.cxqs0u844kgr.ap-northeast-2.rds.amazonaws.com",
        uid="scott",
        pwd="tigerlion2401$",
        database=db
    )
    
    # con = oracledb.connect( user="scott", password="tiger", dsn="localhost:1521/xe")
    # 연결된 DB 지시자(커서) 생성
    cursor = con.cursor()
    
    rows = '50'
    # url = 'http://api.nongsaro.go.kr/service/garden/'
    key = '20240513TIQYSE6AZKGZBGVUJMZFFW'
    list_type = 'gardenList?'

    url = f'http://api.nongsaro.go.kr/service/garden/{list_type}'
    api_key = f'apiKey={key}'
    num_of_rows = f'&numOfRows={rows}'

    r = requests.get(url + api_key + num_of_rows)
    data = BeautifulSoup(r.text, 'lxml-xml')
    cntnts_no = data.find_all('cntntsNo')
    cntnts_sj = data.find_all('cntntsSj')
    imgs = data.find_all('rtnFileUrl')
    
        
    moran_category = {}
    moran_category["직립형"] = 1
    moran_category["관목형"] = 2
    moran_category["덩굴성"] = 3
    moran_category["풀모양"] = 4
    moran_category["로제트형"] = 5
    moran_category["다육형"] = 6
    
    for i, j, k in zip(cntnts_no, cntnts_sj, imgs):
        no = i.text
        name = j.text.replace("'", '"')
        img = k.text
        print(no + ": " + name)
        
        # 가져온 데이터 DICT 변환 (xml -> json 변환)
        r = requests.post(
            'http://api.nongsaro.go.kr/service/garden/gardenDtl',
            data={
                "apiKey": key,
                "cntntsNo": no
            }
        )
        xml = xmltodict.parse(r.text) ["response"] ["body"] ["item"]
        
        info = {}
        info["clCodeNm"] = str(xml["grwhstleCodeNm"]).replace("\"", "''") # 생육형태
        info["fncltyInfo"] = str(xml["fncltyInfo"]).replace("\"", "''") # 기능성 정보
        info["speclmanageInfo"] = str(xml["speclmanageInfo"]).replace("\"", "''") # 특별관리 정보
        info["adviseInfo"] = str(xml["adviseInfo"]).replace("\"", "''") # 조언정보
        info["watercycleAutumnCodeNm"] = str(xml["watercycleAutumnCodeNm"]).replace("\"", "''") # 물주기 봄
        info["watercycleSprngCodeNm"] = str(xml["watercycleSprngCodeNm"]).replace("\"", "''") # 물주기 여름
        info["watercycleSummerCodeNm"] = str(xml["watercycleSummerCodeNm"]).replace("\"", "''") # 물주기 가을
        info["watercycleWinterCodeNm"] = str(xml["watercycleWinterCodeNm"]).replace("\"", "''") # 물주기 겨울
        info["managedemanddoCodeNm"] = str(xml["managedemanddoCodeNm"]).replace("\"", "''") # 관리요구도 
        info["managelevelCodeNm"] = str(xml["managelevelCodeNm"]).replace("\"", "''") # 관리수준
        info["postngplaceCodeNm"] = str(xml["postngplaceCodeNm"]).replace("\"", "''") # 배치장소 
        
        # print(info)
        
        json_pras = json.loads(json.dumps(info, ensure_ascii=False).replace("'", "''").replace("\n", ""))
        
        # JSON 문자열을 SQL에 안전하게 포함
        json_desc = json.dumps(json_pras, ensure_ascii=False).replace("'", "''")
        # print(json_desc)
        
        # insert into product
        price = int(np.random.randint(4000, 700000, 1))
        quantity = int(np.random.randint(0, 15, 1))
        dcrate = int(np.random.randint(0, 90, 1))
        wholesale = price - int(float(dcrate / 100) * price)
        
        cate_gory = 0
        for c in moran_category:
            if c in info["clCodeNm"][0:5]:
                cate_gory = moran_category[c]
                break
        print(f"categroy : {cate_gory}, price : {price}, quantity : {quantity}, dcrate : {dcrate}, wholesale : {wholesale}")
        
        query = f"""
            INSERT INTO MORANMORAN.PRODUCT (
                pd_id, c_id, pd_name, pd_price, pd_description, 
                pd_quantity, pd_wholesale, pd_dcrate,
                pd_is_maintain, pd_retention_period)
            VALUES ({no}, {cate_gory}, '{name}', {price}, '{json_desc}', {quantity}, {wholesale}, {dcrate}, 0, NULL)
        """
        cursor.execute(query)
        cursor.execute('commit') # sqldeveloper에 커밋
        
        cnt = 0
        for url in img.split("|"):
            query = f" insert into MORANMORAN.PRODUCT_IMG  values ({no}, {cnt}, '{url}') "
            cursor.execute(query)
            cursor.execute('commit') # sqldeveloper에 커밋
            # print(url)
            cnt += 1
            if cnt >= 3: break
        print("이미지 저장완료 \n")
        
    cursor.close()
    con.close()

except Exception as e:
    print(e)