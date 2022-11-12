export class ExportacaoUtil {

    public static download(downloadUrl: any, filename: any) {
        const a: any = document.createElement('a');
        a.style = 'display: none';
        a.href = downloadUrl;
        a.download = filename;
        document.body.appendChild(a);
        a.click();
    }
}