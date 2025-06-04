# luckydraw

### Change userId

$Powershell
```
Invoke-WebRequest -Method POST -Uri "http://localhost:8080/api/lucky-draw/draw?userId=user123"
```
$bash
```
curl -X POST -H "Accept-Charset: UTF-8" "http://localhost:8080/api/lucky-draw/draw?userId=user123"
```
